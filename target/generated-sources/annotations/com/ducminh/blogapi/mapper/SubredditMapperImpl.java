package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.SubredditRequest;
import com.ducminh.blogapi.dto.response.SubredditResponse;
import com.ducminh.blogapi.entity.Subreddit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-11T08:18:35+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class SubredditMapperImpl implements SubredditMapper {

    @Override
    public Subreddit toSubreddit(SubredditRequest request) {
        if ( request == null ) {
            return null;
        }

        Subreddit.SubredditBuilder subreddit = Subreddit.builder();

        subreddit.name( request.getName() );
        subreddit.description( request.getDescription() );
        subreddit.privacy( request.getPrivacy() );

        return subreddit.build();
    }

    @Override
    public SubredditResponse toSubredditResponse(Subreddit subreddit) {
        if ( subreddit == null ) {
            return null;
        }

        SubredditResponse subredditResponse = new SubredditResponse();

        subredditResponse.setName( subreddit.getName() );
        subredditResponse.setDescription( subreddit.getDescription() );
        subredditResponse.setPrivacy( subreddit.getPrivacy() );

        return subredditResponse;
    }
}
