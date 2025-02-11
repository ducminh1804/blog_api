package com.ducminh.blogapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invalid_token")
public class InvalidToken {
    @Id
    @Column(name = "jit")
    private String jit;
    @Column(name = "expiriy_time")
    private Date expiryTime;

}
