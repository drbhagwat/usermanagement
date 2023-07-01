package com.example.usermanagement.repository;

import com.example.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("from User u left join u.role r where r.roleName = :#{#roleName}")
  List<User> findUsersWithRole(@Param("roleName") String roleName);

  @Query("from User u left join u.role r where r.roleName != :#{#roleName}")
  List<User> findUsersWithNonAdminRole(@Param("roleName") String roleName);

  Optional<User> findByUsername(String username);
}
