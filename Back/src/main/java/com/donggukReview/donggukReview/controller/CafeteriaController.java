package com.donggukReview.donggukReview.controller;

import com.donggukReview.donggukReview.common.AuthUser;
import com.donggukReview.donggukReview.dto.CafeteriaDetailResponseDTO;
import com.donggukReview.donggukReview.dto.CafeteriaResponseDTO;
import com.donggukReview.donggukReview.dto.CategoryResponseDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.CafeteriaDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.RatingsDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.ReviewDTO;
import com.donggukReview.donggukReview.entity.Review;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.service.CafeteriaService;
import com.donggukReview.donggukReview.service.RatingsService;
import com.donggukReview.donggukReview.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class CafeteriaController {

    private CafeteriaService cafeteriaService;
    private RatingsService ratingsService;
    private ReviewService reviewService;
    @PostMapping("/cafeteria")
    public ResponseEntity<CafeteriaDTO> createCafeteria(@RequestPart(value = "data") CafeteriaDTO cafeteriaDTO, @RequestPart(value = "file", required = false) MultipartFile file) {
        CafeteriaDTO cafeteria = cafeteriaService.createCafeteria(cafeteriaDTO, file);
        return new ResponseEntity<>(cafeteria, HttpStatus.CREATED);
    }
    @GetMapping("/cafeteria/{id}")
    public ResponseEntity<CafeteriaDetailResponseDTO> getCafeteriaById(@AuthUser Users users, @PathVariable("id") Long cafeteriaId) {
        CafeteriaDetailResponseDTO cafeteria = cafeteriaService.getCafeteriaDetailById(users, cafeteriaId);
        return ResponseEntity.ok(cafeteria);
    }
    @GetMapping
    public ResponseEntity<List<CafeteriaResponseDTO>> getAllCafeterias(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category) {

        List<CafeteriaResponseDTO> cafeterias;

        if (name != null && !name.isEmpty() && category != null && !category.isEmpty()) {
            cafeterias = cafeteriaService.getCafeteriasByNameAndCategory(name, category);
        } else if (name != null && !name.isEmpty()) {
            cafeterias = cafeteriaService.getCafeteriasByName(name);
        } else if (category != null && !category.isEmpty()) {
            cafeterias = cafeteriaService.getCafeteriasByCategory(category);
        } else {
            cafeterias = cafeteriaService.getAllCafeterias();
        }

        return ResponseEntity.ok(cafeterias);
    }
    @PatchMapping("/cafeteria/{id}")
    public ResponseEntity<CafeteriaDTO> patchCafeteria(@PathVariable("id") Long cafeteriaId, @RequestBody CafeteriaDTO cafeteriaDTO, @RequestPart(value = "file", required = false) MultipartFile file) {
        CafeteriaDTO cafeteria = cafeteriaService.patchCafeteria(cafeteriaId, cafeteriaDTO, file);
        return ResponseEntity.ok(cafeteria);
    }
    @DeleteMapping("/cafeteria/{id}")
    public ResponseEntity<String> deleteCafeteria(@PathVariable("id") Long cafeteriaId) {
        cafeteriaService.deleteCafeteria(cafeteriaId);
        String okMsg = String.format("%s deleted successfully", cafeteriaId);
        return ResponseEntity.ok(okMsg);
    }

    @GetMapping("/cafeteria/ratings/{id}")
    public ResponseEntity<RatingsDTO> getRatingsById(@PathVariable("id") Long cafeteriaId) {
        RatingsDTO ratings = ratingsService.getRatingsById(cafeteriaId);
        return  ResponseEntity.ok(ratings);
    }

    @GetMapping("/cafeteria/reviews/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable("id") Long cafeteriaId) {
        List<Review> reviews = reviewService.getReviewByCafeteriaId(cafeteriaId);
        if (reviews == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review가 없습니다.");
        }
        else {
            List<ReviewDTO> reviewDTOList = new ArrayList<>();
            for (Review review : reviews) {
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setId(review.getId());
                reviewDTO.setUserId(review.getUserId());
                reviewDTO.setCafeteriaId(review.getCafeteriaId());
                reviewDTO.setReviewRecommended(review.getReviewRecommended());
                reviewDTO.setReviewContents(review.getReviewContents());
                reviewDTO.setReviewRatings(review.getReviewRatings());
                reviewDTO.setCafeteriaId(review.getCafeteriaId());

                reviewDTOList.add(reviewDTO);
            }
            return ResponseEntity.ok(reviewDTOList);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategoryList() {
        CategoryResponseDTO category = cafeteriaService.getCategoryList();
        return ResponseEntity.ok(category);
    }
}
