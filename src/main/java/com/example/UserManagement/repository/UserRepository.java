package com.example.UserManagement.repository;

import com.example.UserManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  @Query("from User u left join u.roles r where r.roleName = :#{#roleName}")
  List<User> findUsersWithRole(@Param("roleName") String roleName);
}
