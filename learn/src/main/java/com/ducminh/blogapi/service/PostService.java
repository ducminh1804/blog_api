package com.ducminh.blogapi.service;

import com.ducminh.blogapi.constant.ApiMethod;
import com.ducminh.blogapi.dto.MessageRedisType;
import com.ducminh.blogapi.dto.request.PostRequest;
import com.ducminh.blogapi.dto.response.PostResponse;
import com.ducminh.blogapi.entity.Post;
import com.ducminh.blogapi.entity.PostEs;
import com.ducminh.blogapi.entity.Tag;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.constant.ErrorCode;
import com.ducminh.blogapi.mapper.PostMapper;
import com.ducminh.blogapi.repository.jpa.PostRepository;
import com.ducminh.blogapi.repository.jpa.TagRepository;
import com.ducminh.blogapi.repository.jpa.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableCaching

public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private PublisherMessage publisherMessage;
    @Autowired
    private ElasticsearchOperations operations;

    public PostResponse createPost(PostRequest request, Principal principal) {
        List<Tag> tags = tagRepository.findAllByNameIn(new ArrayList<>(request.getTags()));
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Post post = postMapper.toPost(request);
        post.setUser(user);
        post.setTags(new HashSet<>(tags));
        PostResponse postResponse = postMapper.toPostResponse(postRepository.save(post));
        postResponse.setUsername(user.getUsername());
        return postResponse;
    }

    public void deletePost(String postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public PostResponse createPostImage(String title, String kind, List<String> tagList, MultipartFile file, Principal principal) throws IOException {
        Map cloud = cloudinaryService.uploadImage(file);
        String body = (String) cloud.get("url");
        List<Tag> tags = tagRepository.findAllByNameIn(tagList);
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Post post = Post.builder()
                .user(user)
                .title(title)
                .body(body)
                .kind(kind)
                .tags(new HashSet<>(tags))
                .build();
        PostResponse postResponse = postMapper.toPostResponse(postRepository.save(post));
        postResponse.setUsername(user.getUsername());

        PostEs postEs = postMapper.toPostEs(postResponse);
        MessageRedisType<PostEs> postEsMessageRedisType = MessageRedisType.<PostEs>builder()
                .apiMethod(ApiMethod.POST)
                .value(postEs)
                .build();
        publisherMessage.send(postEsMessageRedisType);
        log.info("postes {}", postEs.toString());
        return postResponse;
    }

    @Transactional
    public PostResponse createPostVideo(String title, String kind, List<String> tagList, MultipartFile file, Principal principal) throws IOException {
        Map cloud = cloudinaryService.uploadVideo(file);
        String body = (String) cloud.get("url");
        List<Tag> tags = tagRepository.findAllByNameIn(tagList);
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Post post = Post.builder()
                .user(user)
                .title(title)
                .body(body)
                .kind(kind)
                .tags(new HashSet<>(tags))
                .build();
        PostResponse postResponse = postMapper.toPostResponse(postRepository.save(post));
        postResponse.setUsername(user.getUsername());
        return postResponse;
    }

    public List<PostResponse> getPostsPagination(Instant createAt) {
        List<PostResponse> postResponses = postRepository.getPostsPagination(createAt)
                .stream().map(postMapper::toPostResponse).collect(Collectors.toList());
        return postResponses;
    }

    @Cacheable("post")
    public PostResponse getPostsById(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        System.out.println("chao");
        PostResponse postResponse = postMapper.toPostResponse(post);
        postResponse.setUsername(post.getUser().getUsername());
        postResponse.setCreatedAt(post.getCreatedAt());
        return postResponse;
    }

    public List<String> searchPostsBySimilarTitle(String title, Pageable pageable) {
        Query query = NativeQuery.builder()
                .withQuery(q -> q
                        .match(m -> m
                                .field("title")
                                .query(title)
                        )
                )
                .withFields("_id")
                .withPageable(pageable)
                .build();
        SearchHits<PostEs> postEsSearchHit = operations
                .search(query, PostEs.class);
        List<String> ids = postEsSearchHit.stream()
                .map(SearchHit::getId)
                .collect(Collectors.toList());
        return ids;
    }

    public List<PostResponse> getPostsBySimilarTitle(String title, Pageable pageable) {
        List<String> ids = searchPostsBySimilarTitle(title, pageable);
        List<PostResponse> postResponses = postRepository.findAllById(ids)
                .stream()
                .map(item -> {
                            PostResponse postResponse = postMapper.toPostResponse(item);
                            postResponse.setUsername(item.getUser().getId());
                            return postResponse;
                        }
                )
                .collect(Collectors.toList());
        return postResponses;
    }
}

