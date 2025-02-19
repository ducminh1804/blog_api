package com.ducminh.blogapi.service;

import com.ducminh.blogapi.dto.request.PostRequest;
import com.ducminh.blogapi.dto.response.PostResponse;
import com.ducminh.blogapi.entity.Post;
import com.ducminh.blogapi.entity.Tag;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.constant.ErrorCode;
import com.ducminh.blogapi.mapper.PostMapper;
import com.ducminh.blogapi.repository.PostRepository;
import com.ducminh.blogapi.repository.TagRepository;
import com.ducminh.blogapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Service
@Slf4j
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

}

