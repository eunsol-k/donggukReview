package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.dto.EntityDTO.CafeteriaDTO;

import java.util.List;

public interface CafeteriaService {
    CafeteriaDTO createCafeteria(CafeteriaDTO cafeteriaDTO);
    CafeteriaDTO getCafeteriaById(Long cafeteriaId);
    List<CafeteriaDTO> getAllCafeterias();
    CafeteriaDTO patchCafeteria(Long cafeteriaId, CafeteriaDTO cafeteriaDTO);
    void deleteCafeteria(Long cafeteriaId);

}
