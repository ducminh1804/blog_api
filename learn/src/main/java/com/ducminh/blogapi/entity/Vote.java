package com.ducminh.blogapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "votes")
public class Vote {
    @EmbeddedId
    private VoteId voteId;

    @Column(name = "vote")
    private int vote;

}
