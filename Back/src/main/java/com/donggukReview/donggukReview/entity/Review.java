package com.donggukReview.donggukReview.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "review")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_contents")
    private String reviewContents;

    @Column(name = "review_ratings_service")
    private String reviewRatingsService;

    @Column(name = "review_ratings_price")
    private String reviewRatingsPrice;

    @Column(name = "review_ratings_flavor")
    private String reviewRatingsFlavor;

    @Column(name = "review_ratings_total")
    private String reviewRatingsTotal;

    @Column(name = "review_recommended")
    private Long reviewRecommended;

    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;

    @Column(name = "cafeteria_id")
    private Long cafeteriaId;
}