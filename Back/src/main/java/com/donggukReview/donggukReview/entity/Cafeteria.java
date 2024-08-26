package com.donggukReview.donggukReview.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "cafeteria")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cafeteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cafeteria_name")
    private String cafeteriaName;

    @Column(name = "cafeteria_category")
    private String cafeteriaCategory;

    @Column(name = "cafeteria_phone")
    private String cafeteriaPhone;

    @Column(name = "cafeteria_address")
    private String cafeteriaAddress;

    @Column(name = "cafeteria_image_id")
    private Long cafeteriaImageId;
}