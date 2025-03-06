package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.MessageRequest;
import com.ducminh.blogapi.dto.response.MessageResponse;
import com.ducminh.blogapi.entity.Message;
import com.ducminh.blogapi.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-06T14:34:55+0700",
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

        message.setRecipentId( request.getRecipentId() );
        message.setContent( request.getContent() );

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
