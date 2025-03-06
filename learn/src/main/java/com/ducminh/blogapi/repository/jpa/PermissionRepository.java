package com.ducminh.blogapi.repository.jpa;

import com.ducminh.blogapi.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findByName(String name);

    @Query("Select p from Permission p where p.name IN :names")
    List<Permission> findAllByNameIn(@Param("names") List<String> names);
}
