package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.MessageRequest;
import com.ducminh.blogapi.dto.response.MessageResponse;
import com.ducminh.blogapi.entity.Message;
import com.ducminh.blogapi.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-10T16:30:04+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public Message toMessage(MessageRequest request) {
        if ( request == null ) {
            return null;
        }

        Message message = new Message();

        message.setUser( messageRequestToUser( request ) );
        message.setRecipentId( request.getRecipentId() );
        message.setContent( request.getContent() );
        message.setCreatedAt( request.getCreatedAt() );

        return message;
    }

    @Override
    public MessageResponse toMessageResponse(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageResponse.MessageResponseBuilder messageResponse = MessageResponse.builder();

        messageResponse.senderId( messageUserId( message ) );
        messageResponse.id( message.getId() );
        messageResponse.recipentId( message.getRecipentId() );
        messageResponse.content( message.getContent() );

        return messageResponse.build();
    }

    protected User messageRequestToUser(MessageRequest messageRequest) {
        if ( messageRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( messageRequest.getSenderId() );

        return user.build();
    }

    private String messageUserId(Message message) {
        if ( message == null ) {
            return null;
        }
        User user = message.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
