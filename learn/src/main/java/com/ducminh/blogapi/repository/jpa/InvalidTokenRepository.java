package com.ducminh.blogapi.repository.jpa;

import com.ducminh.blogapi.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {

}