package com.ducminh.blogapi.dto.request;

import com.ducminh.blogapi.constant.PrivacyEnum;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubredditRequest {

    private String name;

    private String description;

    private PrivacyEnum privacy;
}
