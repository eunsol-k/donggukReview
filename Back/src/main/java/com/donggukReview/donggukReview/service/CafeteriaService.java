package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.dto.CafeteriaDetailResponseDTO;
import com.donggukReview.donggukReview.dto.CafeteriaResponseDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.CafeteriaDTO;
import com.donggukReview.donggukReview.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CafeteriaService {
    CafeteriaDTO createCafeteria(CafeteriaDTO cafeteriaDTO, MultipartFile file);

    CafeteriaDetailResponseDTO getCafeteriaById(Users users, Long cafeteriaId);
    List<CafeteriaResponseDTO> getAllCafeterias();
    List<CafeteriaResponseDTO> getCafeteriasByName(String name);
    List<CafeteriaResponseDTO> getCafeteriasByCategory(String category);
    List<CafeteriaResponseDTO> getCafeteriasByNameAndCategory(String name, String category);
    CafeteriaDTO patchCafeteria(Long cafeteriaId, CafeteriaDTO cafeteriaDTO, MultipartFile file);
    void deleteCafeteria(Long cafeteriaId);
    boolean isExistsCafeteria(Long cafeteriaId);
}
