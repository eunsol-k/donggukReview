package com.donggukReview.donggukReview.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "ratings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ratings_service")
    private String ratingsService;

    @Column(name = "ratings_price")
    private String ratingsPrice;

    @Column(name = "ratings_flavor")
    private String ratingsFlavor;

    @Column(name = "ratings_total")
    private String ratingsTotal;

    @Column(name = "cafeteria_id")
    private Long cafeteriaId;
}