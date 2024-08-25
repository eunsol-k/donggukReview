package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUserId(String userId);
    List<Users> findByUserRole(String role);
    boolean existsByUserId(String userId);
}
