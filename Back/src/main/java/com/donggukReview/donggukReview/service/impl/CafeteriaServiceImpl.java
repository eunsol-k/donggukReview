package com.donggukReview.donggukReview.service.impl;

import com.donggukReview.donggukReview.dto.EntityDTO.CafeteriaDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.mapper.CafeteriaMapper;
import com.donggukReview.donggukReview.entity.Cafeteria;
import com.donggukReview.donggukReview.exception.ResourceNotFoundException;
import com.donggukReview.donggukReview.repository.CafeteriaRepository;
import com.donggukReview.donggukReview.service.CafeteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CafeteriaServiceImpl implements CafeteriaService {

    private final CafeteriaRepository cafeteriaRepository;
    @Override
    public CafeteriaDTO createCafeteria(CafeteriaDTO cafeteriaDTO) {
        Cafeteria cafeteria = CafeteriaMapper.mapToCafeteria(cafeteriaDTO);
        Cafeteria savedCafeteria = cafeteriaRepository.save(cafeteria);
        return CafeteriaMapper.mapToCafeteriaDto(savedCafeteria);
    }

    @Override
    @Transactional(readOnly = true)
    public CafeteriaDTO getCafeteriaById(Long cafeteriaId) {
        String errMsg = String.format("Data is not exists %s", cafeteriaId);
        Cafeteria cafeteria = cafeteriaRepository.findById(cafeteriaId)
                .orElseThrow(() -> new ResourceNotFoundException(errMsg, HttpStatus.NOT_FOUND));
        return CafeteriaMapper.mapToCafeteriaDto(cafeteria);
    }

    @Override
    public List<CafeteriaDTO> getAllCafeterias() {
        List<Cafeteria> cafeterias = cafeteriaRepository.findAll();
        return cafeterias.stream().map(CafeteriaMapper :: mapToCafeteriaDto).toList();
    }

    @Override
    public CafeteriaDTO patchCafeteria(Long cafeteriaId, CafeteriaDTO cafeteriaDTO) {
        String errMsg = String.format("Data is not exists %s", cafeteriaId);
        Cafeteria cafeteria = cafeteriaRepository.findById(cafeteriaId)
                .orElseThrow(() -> new ResourceNotFoundException(errMsg, HttpStatus.NOT_FOUND));
        if (cafeteriaDTO.getCafeteriaName() != null) {
            cafeteria.setCafeteriaName(cafeteriaDTO.getCafeteriaName());
        }
        if (cafeteriaDTO.getCafeteriaCategory() != null) {
            cafeteria.setCafeteriaCategory(cafeteriaDTO.getCafeteriaCategory());
        }
        if (cafeteriaDTO.getCafeteriaPhone() != null) {
            cafeteria.setCafeteriaPhone(cafeteriaDTO.getCafeteriaPhone());
        }
        if (cafeteriaDTO.getCafeteriaAddress() != null) {
            cafeteria.setCafeteriaAddress(cafeteriaDTO.getCafeteriaAddress());
        }
        if (cafeteriaDTO.getCafeteriaImageId() != null) {
            cafeteria.setCafeteriaImageId(cafeteriaDTO.getCafeteriaImageId());
        }

        Cafeteria savedCafeteria = cafeteriaRepository.save(cafeteria);

        return CafeteriaMapper.mapToCafeteriaDto(savedCafeteria);
    }

    @Override
    public void deleteCafeteria(Long cafeteriaId) {
        String errMsg = String.format("Data is not exists %s", cafeteriaId);
        Cafeteria cafeteria = cafeteriaRepository.findById(cafeteriaId)
                .orElseThrow(() -> new ResourceNotFoundException(errMsg, HttpStatus.NOT_FOUND));
        cafeteriaRepository.delete(cafeteria);
    }

    @Override
    public boolean isExistsCafeteria(Long cafeteriaId) {
        return cafeteriaRepository.existsById(cafeteriaId);
    }
}
