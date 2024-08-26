package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Cafeteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeteriaRepository extends JpaRepository<Cafeteria, Long> {

    List<Cafeteria> findByCafeteriaNameContainingIgnoreCase(String cafeteriaName);
    List<Cafeteria> findByCafeteriaCategoryContainingIgnoreCase(String cafeteriaCategory);
    List<Cafeteria> findByCafeteriaNameContainingIgnoreCaseAndCafeteriaCategoryContainingIgnoreCase(String cafeteriaName, String cafeteriaCategory);
}