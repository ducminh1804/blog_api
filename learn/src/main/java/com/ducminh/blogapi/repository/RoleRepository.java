package com.ducminh.blogapi.repository;

import com.ducminh.blogapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//cung loai thi extend, khac thi imple
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
