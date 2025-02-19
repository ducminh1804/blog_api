package com.ducminh.blogapi.repository;

import com.ducminh.blogapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, String> {
    @Query("Select t from Tag t where t.name IN :names")
    List<Tag> findAllByNameIn(@Param("names") List<String> names);
}
