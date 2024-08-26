package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.dto.CafeteriaResponseDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.CafeteriaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CafeteriaService {
    CafeteriaDTO createCafeteria(CafeteriaDTO cafeteriaDTO, MultipartFile file);

    CafeteriaResponseDTO getCafeteriaById(Long cafeteriaId);
    List<CafeteriaResponseDTO> getAllCafeterias();
    List<CafeteriaResponseDTO> getCafeteriasByName(String name);
    List<CafeteriaResponseDTO> getCafeteriasByCategory(String category);
    List<CafeteriaResponseDTO> getCafeteriasByNameAndCategory(String name, String category);
    CafeteriaDTO patchCafeteria(Long cafeteriaId, CafeteriaDTO cafeteriaDTO, MultipartFile file);
    void deleteCafeteria(Long cafeteriaId);
    boolean isExistsCafeteria(Long cafeteriaId);
}
