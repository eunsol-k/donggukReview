package com.donggukReview.donggukReview.controller;

import com.donggukReview.donggukReview.dto.CafeteriaResponseDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.CafeteriaDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.ImageDTO;
import com.donggukReview.donggukReview.service.CafeteriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class CafeteriaController {

    private CafeteriaService cafeteriaService;
    @PostMapping("/cafeteria")
    public ResponseEntity<CafeteriaDTO> createCafeteria(@RequestBody CafeteriaDTO cafeteriaDTO, @RequestPart(value = "file", required = false) MultipartFile file) {
        CafeteriaDTO cafeteria = cafeteriaService.createCafeteria(cafeteriaDTO, file);
        return new ResponseEntity<>(cafeteria, HttpStatus.CREATED);
    }
    @GetMapping("/cafeteria/{id}")
    public ResponseEntity<CafeteriaResponseDTO> getCafeteriaById(@PathVariable("id") Long cafeteriaId) {
        CafeteriaResponseDTO cafeteria = cafeteriaService.getCafeteriaById(cafeteriaId);
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

//    @PostMapping("/cafeteria/{id}/like")
//    public ResponseEntity<String> createLike(@PathVariable("id") Long cafeteriaId) {
//        cafeteriaService.createLike(cafeteriaId);
//
//    }

}
