package com.ducminh.blogapi.entity;

import com.ducminh.blogapi.constant.PrivacyEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follows")
public class Follow {
    @EmbeddedId
    private FollowId id;

    private PrivacyEnum privacyEnum;
}
