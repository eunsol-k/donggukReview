package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    void deleteByCafeteriaId(Long cafeteriaId);
}
