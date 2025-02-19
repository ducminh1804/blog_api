package com.ducminh.blogapi.repository;

import com.ducminh.blogapi.entity.Post;
import com.ducminh.blogapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {


}
