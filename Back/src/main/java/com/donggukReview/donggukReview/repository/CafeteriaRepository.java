package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Cafeteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeteriaRepository extends JpaRepository<Cafeteria, Long> {

    List<Cafeteria> findByCafeteriaNameContainingIgnoreCase(String cafeteriaName);
    List<Cafeteria> findByCafeteriaCategoryContainingIgnoreCase(String cafeteriaCategory);
    List<Cafeteria> findByCafeteriaNameContainingIgnoreCaseAndCafeteriaCategoryContainingIgnoreCase(String cafeteriaName, String cafeteriaCategory);
}