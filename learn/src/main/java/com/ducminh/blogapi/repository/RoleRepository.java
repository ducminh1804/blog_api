package com.ducminh.blogapi.repository;

import com.ducminh.blogapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//cung loai thi extend, khac thi imple
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);

    @Query("Select r from Role r where r.name IN :roles")
    List<Role> findAllByNameIn(@Param("roles") List<String> roles);
}
