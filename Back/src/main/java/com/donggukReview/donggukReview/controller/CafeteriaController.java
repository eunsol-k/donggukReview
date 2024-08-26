package com.donggukReview.donggukReview.controller;

import com.donggukReview.donggukReview.dto.CafeteriaDTO;
import com.donggukReview.donggukReview.service.CafeteriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class CafeteriaController {

    private CafeteriaService cafeteriaService;
    @PostMapping("/cafeteria")
    public ResponseEntity<CafeteriaDTO> createCafeteria(@RequestBody CafeteriaDTO cafeteriaDTO) {
        CafeteriaDTO cafeteria = cafeteriaService.createCafeteria(cafeteriaDTO);
        return new ResponseEntity<>(cafeteria, HttpStatus.CREATED);
    }
    @GetMapping("/cafeteria/{id}")
    public ResponseEntity<CafeteriaDTO> getCafeteriaById(@PathVariable("id") Long cafeteriaId) {
        CafeteriaDTO cafeteria = cafeteriaService.getCafeteriaById(cafeteriaId);
        return ResponseEntity.ok(cafeteria);
    }
    @GetMapping
    public ResponseEntity<List<CafeteriaDTO>> getAllCafeterias(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category) {

        List<CafeteriaDTO> cafeterias;

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
    public ResponseEntity<CafeteriaDTO> patchCafeteria(@PathVariable("id") Long cafeteriaId, @RequestBody CafeteriaDTO cafeteriaDTO) {
        CafeteriaDTO cafeteria = cafeteriaService.patchCafeteria(cafeteriaId, cafeteriaDTO);
        return ResponseEntity.ok(cafeteria);
    }
    @DeleteMapping("/cafeteria/{id}")
    public ResponseEntity<String> deleteCafeteria(@PathVariable("id") Long cafeteriaId) {
        cafeteriaService.deleteCafeteria(cafeteriaId);
        String okMsg = String.format("%s deleted successfully", cafeteriaId);
        return ResponseEntity.ok(okMsg);
    }

    @PostMapping("/cafeteria/{id}/like")
    public ResponseEntity<String> createLike(@PathVariable("id") Long cafeteriaId) {
        cafeteriaService.createLike(cafeteriaId);

    }

}
