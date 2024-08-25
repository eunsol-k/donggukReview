package com.donggukReview.donggukReview.controller;

import com.donggukReview.donggukReview.dto.CafeteriaDTO;
import com.donggukReview.donggukReview.dto.LikesDTO;
import com.donggukReview.donggukReview.service.CafeteriaService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cafeteria")
public class CafeteriaController {

    private CafeteriaService cafeteriaService;
    @PostMapping
    public ResponseEntity<CafeteriaDTO> createCafeteria(@RequestBody CafeteriaDTO cafeteriaDTO) {
        CafeteriaDTO cafeteria = cafeteriaService.createCafeteria(cafeteriaDTO);
        return new ResponseEntity<>(cafeteria, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CafeteriaDTO> getCafeteriaById(@PathVariable("id") Long cafeteriaId) {
        CafeteriaDTO cafeteria = cafeteriaService.getCafeteriaById(cafeteriaId);
        return ResponseEntity.ok(cafeteria);
    }
    @GetMapping
    public ResponseEntity<List<CafeteriaDTO>> getAllCafeterias() {
        List<CafeteriaDTO> cafeterias = cafeteriaService.getAllCafeterias();
        return ResponseEntity.ok(cafeterias);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CafeteriaDTO> patchCafeteria(@PathVariable("id") Long cafeteriaId, @RequestBody CafeteriaDTO cafeteriaDTO) {
        CafeteriaDTO cafeteria = cafeteriaService.patchCafeteria(cafeteriaId, cafeteriaDTO);
        return ResponseEntity.ok(cafeteria);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCafeteria(@PathVariable("id") Long cafeteriaId) {
        cafeteriaService.deleteCafeteria(cafeteriaId);
        String okMsg = String.format("%s deleted successfully", cafeteriaId);
        return ResponseEntity.ok(okMsg);
    }

}
