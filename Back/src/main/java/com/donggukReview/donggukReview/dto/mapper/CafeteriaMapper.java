package com.donggukReview.donggukReview.dto.mapper;

import com.donggukReview.donggukReview.dto.CafeteriaDTO;
import com.donggukReview.donggukReview.entity.Cafeteria;

public class CafeteriaMapper {
    public static CafeteriaDTO mapToCafeteriaDto(Cafeteria cafeteria) {
        return new CafeteriaDTO(
            cafeteria.getId(),
            cafeteria.getCafeteriaName(),
            cafeteria.getCafeteriaCategory(),
            cafeteria.getCafeteriaPhone(),
            cafeteria.getCafeteriaAddress(),
            cafeteria.getCafeteriaImageId()
        );
    }

    public static Cafeteria mapToCafeteria(CafeteriaDTO cafeteriaDTO) {
        return new Cafeteria(
                cafeteriaDTO.getId(),
                cafeteriaDTO.getCafeteriaName(),
                cafeteriaDTO.getCafeteriaCategory(),
                cafeteriaDTO.getCafeteriaPhone(),
                cafeteriaDTO.getCafeteriaAddress(),
                cafeteriaDTO.getCafeteriaImageId()
        );
    }
}
