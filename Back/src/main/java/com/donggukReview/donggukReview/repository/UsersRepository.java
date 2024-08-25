package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUserId(String userId);
    List<Users> findByUserRole(String role);
}
