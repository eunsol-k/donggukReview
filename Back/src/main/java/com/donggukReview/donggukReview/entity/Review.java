package com.donggukReview.donggukReview.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "review")
@Getter
@Setter
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

//    @Column(name = "review_ratings_service")
//    private String reviewRatingsService;
//
//    @Column(name = "review_ratings_price")
//    private String reviewRatingsPrice;
//
//    @Column(name = "review_ratings_flavor")
//    private String reviewRatingsFlavor;

    @Column(name = "review_ratings_total")
    private String reviewRatingsTotal;

    @Column(name = "review_recommended")
    private Integer reviewRecommended;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "cafeteria_id")
    private Long cafeteriaId;
}