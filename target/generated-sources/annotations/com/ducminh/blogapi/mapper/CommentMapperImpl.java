package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.CommentRequest;
import com.ducminh.blogapi.dto.response.CommentResponse;
import com.ducminh.blogapi.entity.Comment;
import com.ducminh.blogapi.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T00:45:01+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment toComment(CommentRequest request) {
        if ( request == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setParentId( request.getParentId() );
        comment.setContent( request.getContent() );

        return comment;
    }

    @Override
    public CommentResponse toCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setUserId( commentUserId( comment ) );
        commentResponse.setUsername( commentUserUsername( comment ) );
        commentResponse.setId( comment.getId() );
        commentResponse.setContent( comment.getContent() );
        commentResponse.setCreatedAt( comment.getCreatedAt() );
        commentResponse.setVoteDown( comment.getVoteDown() );
        commentResponse.setVoteUp( comment.getVoteUp() );
        commentResponse.setParentId( comment.getParentId() );

        return commentResponse;
    }

    private String commentUserId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String commentUserUsername(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }
}
