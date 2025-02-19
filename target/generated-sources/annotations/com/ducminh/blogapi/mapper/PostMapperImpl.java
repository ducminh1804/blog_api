package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.PostRequest;
import com.ducminh.blogapi.dto.response.PostResponse;
import com.ducminh.blogapi.entity.Post;
import com.ducminh.blogapi.entity.Tag;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T17:59:57+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post toPost(PostRequest postRequest) {
        if ( postRequest == null ) {
            return null;
        }

        Post post = new Post();

        post.setTitle( postRequest.getTitle() );
        post.setBody( postRequest.getBody() );
        post.setKind( postRequest.getKind() );

        return post;
    }

    @Override
    public PostResponse toPostResponse(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse postResponse = new PostResponse();

        postResponse.setId( post.getId() );
        postResponse.setTitle( post.getTitle() );
        postResponse.setBody( post.getBody() );
        Set<Tag> set = post.getTags();
        if ( set != null ) {
            postResponse.setTags( new LinkedHashSet<Tag>( set ) );
        }
        postResponse.setKind( post.getKind() );
        postResponse.setCreatedAt( post.getCreatedAt() );
        postResponse.setUpVoted( post.getUpVoted() );
        postResponse.setDownVoted( post.getDownVoted() );

        return postResponse;
    }
}
