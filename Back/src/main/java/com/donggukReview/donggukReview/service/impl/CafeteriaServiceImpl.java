package com.donggukReview.donggukReview.service.impl;

import com.donggukReview.donggukReview.dto.CafeteriaDetailResponseDTO;
import com.donggukReview.donggukReview.dto.CafeteriaResponseDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.CafeteriaDTO;
import com.donggukReview.donggukReview.dto.CategoryResponseDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.RatingsDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.mapper.CafeteriaMapper;
import com.donggukReview.donggukReview.dto.ReviewResponseDTO;
import com.donggukReview.donggukReview.entity.*;
import com.donggukReview.donggukReview.exception.ResourceNotFoundException;
import com.donggukReview.donggukReview.repository.*;
import com.donggukReview.donggukReview.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CafeteriaServiceImpl implements CafeteriaService {

    private final CafeteriaRepository cafeteriaRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final LikeService likeService;
    private final RatingsService ratingsService;
    private final ReviewService reviewService;
    private final MenuRepository menuRepository;

    @Override
    public CafeteriaDTO createCafeteria(CafeteriaDTO cafeteriaDTO, MultipartFile file) {
        // CafeteriaDTO를 Cafeteria 엔티티로 매핑
        Cafeteria cafeteria = CafeteriaMapper.mapToCafeteria(cafeteriaDTO);

        // Cafeteria 엔티티를 DB에 저장
        Cafeteria savedCafeteria = cafeteriaRepository.save(cafeteria);

        if (file != null) {
            // 이미지 추가
            long imageId = imageService.addImage(savedCafeteria.getId(), file, false);
            savedCafeteria.setCafeteriaImageId(imageId);
        } else {
            savedCafeteria.setCafeteriaImageId(null);
        }

        // 업데이트된 Cafeteria를 DB에 저장
        cafeteriaRepository.save(savedCafeteria);

        // Cafeteria 엔티티를 DTO로 매핑하여 반환
        return CafeteriaMapper.mapToCafeteriaDto(savedCafeteria);
    }

    @Override
    @Transactional(readOnly = true)
    public CafeteriaDetailResponseDTO getCafeteriaDetailById(Users users, Long cafeteriaId) {
        // 예외 메시지 정의
        String errMsg = String.format("Data does not exist for ID %s", cafeteriaId);

        // 식당 정보를 DB에서 조회
        Cafeteria cafeteria = cafeteriaRepository.findById(cafeteriaId)
                .orElseThrow(() -> new ResourceNotFoundException(errMsg, HttpStatus.NOT_FOUND));

        // 식당 이미지 정보를 가져옴
        Optional<Image> imageOptional = imageService.getCafeteriaImgByCafeteriaId(cafeteriaId);

        // 식당 정보 DTO 생성
        CafeteriaResponseDTO cafeteriaResponseDTO = new CafeteriaResponseDTO();
        cafeteriaResponseDTO.setCafeteriaId(cafeteria.getId());
        cafeteriaResponseDTO.setCafeteriaName(cafeteria.getCafeteriaName());
        cafeteriaResponseDTO.setCafeteriaCategory(cafeteria.getCafeteriaCategory());
        cafeteriaResponseDTO.setCafeteriaPhone(cafeteria.getCafeteriaPhone());
        cafeteriaResponseDTO.setCafeteriaAddress(cafeteria.getCafeteriaAddress());

        // Optional을 통해 이미지 정보가 존재할 경우 설정
        imageOptional.ifPresent(image -> cafeteriaResponseDTO.setStoredFilePath(image.getStoredFilePath()));

        // CafeteriaDetailResponseDTO 생성 및 설정
        CafeteriaDetailResponseDTO cafeteriaDetailResponseDTO = new CafeteriaDetailResponseDTO();

        // 리뷰 목록 가져오기 (리뷰가 없을 경우 빈 리스트 반환)
        List<Review> reviews = reviewService.getReviewByCafeteriaId(cafeteriaId);
        if (reviews == null) {
            // 리뷰 리스트가 비어있을 경우 null 설정
            cafeteriaDetailResponseDTO.setReviewResponseDTOList(null);
        } else {
            List<ReviewResponseDTO> reviewResponseDTOList = reviews.stream()
                    .map(review -> {
                        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
                        reviewResponseDTO.setReviewContents(review.getReviewContents());
                        reviewResponseDTO.setReviewRatings(review.getReviewRatings());
                        reviewResponseDTO.setReviewRecommended(review.getReviewRecommended());
                        return reviewResponseDTO;
                    }).toList();
            cafeteriaDetailResponseDTO.setReviewResponseDTOList(reviewResponseDTOList);
        }

        // 평점 가져오기 (평점이 없을 경우 null로 설정)
        RatingsDTO ratings = ratingsService.getRatingsById(cafeteriaId);

        // ratings가 null인 경우 null을 설정, 아니면 ratings 값 사용
        cafeteriaDetailResponseDTO.setCafeteriaRating(ratings != null ? ratings.getRatings() : null);

        // users가 null이 아닌 경우에만 likeService 호출
        cafeteriaDetailResponseDTO.setLike(users != null && likeService.isExistsLike(users, cafeteriaId));

        cafeteriaDetailResponseDTO.setCafeteriaResponseDTO(cafeteriaResponseDTO);

        return cafeteriaDetailResponseDTO;
    }

    @Override
    public List<CafeteriaResponseDTO> getAllCafeterias() {
        // 모든 Cafeteria 객체를 조회
        List<Cafeteria> cafeterias = cafeteriaRepository.findAll();

        // 모든 Image 객체를 조회
        List<Image> images = imageRepository.findAll();

        // CafeteriaResponseDTO 리스트 초기화
        List<CafeteriaResponseDTO> cafeteriaResponseDTOList = new ArrayList<>();

        // Cafeteria 리스트를 순회하며 각 Cafeteria에 해당하는 DTO를 생성
        for (Cafeteria cafeteria : cafeterias) {
            CafeteriaResponseDTO cafeteriaResponseDTO = new CafeteriaResponseDTO();
            cafeteriaResponseDTO.setCafeteriaId(cafeteria.getId());
            cafeteriaResponseDTO.setCafeteriaName(cafeteria.getCafeteriaName());
            cafeteriaResponseDTO.setCafeteriaCategory(cafeteria.getCafeteriaCategory());
            cafeteriaResponseDTO.setCafeteriaPhone(cafeteria.getCafeteriaPhone());
            cafeteriaResponseDTO.setCafeteriaAddress(cafeteria.getCafeteriaAddress());

            // 현재 Cafeteria의 이미지 ID와 일치하는 Image 객체를 찾음
            Image matchingImage = images.stream()
                    .filter(image -> image.getCreatorId().equals(cafeteria.getId()))
                    .findFirst()
                    .orElse(null);

            if (matchingImage != null) {
                cafeteriaResponseDTO.setStoredFilePath(matchingImage.getStoredFilePath());
            }

            cafeteriaResponseDTOList.add(cafeteriaResponseDTO);
        }

        return cafeteriaResponseDTOList;
    }

    @Override
    public List<CafeteriaResponseDTO> getCafeteriasByName(String name) {
        // 이름에 해당하는 모든 Cafeteria 객체를 조회
        List<Cafeteria> cafeterias = cafeteriaRepository.findByCafeteriaNameContainingIgnoreCase(name);

        // CafeteriaResponseDTO 리스트 초기화
        List<CafeteriaResponseDTO> cafeteriaResponseDTOList = new ArrayList<>();

        // 각 Cafeteria 객체를 순회하며 Image 정보를 포함한 DTO를 생성
        for (Cafeteria cafeteria : cafeterias) {
            CafeteriaResponseDTO cafeteriaResponseDTO = new CafeteriaResponseDTO();
            cafeteriaResponseDTO.setCafeteriaId(cafeteria.getId());
            cafeteriaResponseDTO.setCafeteriaName(cafeteria.getCafeteriaName());
            cafeteriaResponseDTO.setCafeteriaCategory(cafeteria.getCafeteriaCategory());
            cafeteriaResponseDTO.setCafeteriaPhone(cafeteria.getCafeteriaPhone());
            cafeteriaResponseDTO.setCafeteriaAddress(cafeteria.getCafeteriaAddress());

            // 현재 Cafeteria의 ID에 해당하는 Image 객체를 조회
            Optional<Image> imageOptional = imageService.getCafeteriaImgByCafeteriaId(cafeteria.getId());

            // 이미지가 존재하는 경우 DTO에 이미지 정보를 설정
            imageOptional.ifPresent(image -> {
                cafeteriaResponseDTO.setStoredFilePath(image.getStoredFilePath());
            });

            cafeteriaResponseDTOList.add(cafeteriaResponseDTO);
        }

        return cafeteriaResponseDTOList;
    }

    @Override
    public List<CafeteriaResponseDTO> getCafeteriasByCategory(String category) {
        // 카테고리에 해당하는 모든 Cafeteria 객체를 조회
        List<Cafeteria> cafeterias = cafeteriaRepository.findByCafeteriaCategoryContainingIgnoreCase(category);

        // CafeteriaResponseDTO 리스트 초기화
        List<CafeteriaResponseDTO> cafeteriaResponseDTOList = new ArrayList<>();

        // 각 Cafeteria 객체를 순회하며 Image 정보를 포함한 DTO를 생성
        for (Cafeteria cafeteria : cafeterias) {
            CafeteriaResponseDTO cafeteriaResponseDTO = new CafeteriaResponseDTO();
            cafeteriaResponseDTO.setCafeteriaId(cafeteria.getId());
            cafeteriaResponseDTO.setCafeteriaName(cafeteria.getCafeteriaName());
            cafeteriaResponseDTO.setCafeteriaCategory(cafeteria.getCafeteriaCategory());
            cafeteriaResponseDTO.setCafeteriaPhone(cafeteria.getCafeteriaPhone());
            cafeteriaResponseDTO.setCafeteriaAddress(cafeteria.getCafeteriaAddress());

            // 현재 Cafeteria의 ID에 해당하는 Image 객체를 조회
            Optional<Image> imageOptional = imageService.getCafeteriaImgByCafeteriaId(cafeteria.getId());

            // 이미지가 존재하는 경우 DTO에 이미지 정보를 설정
            imageOptional.ifPresent(image -> {
                cafeteriaResponseDTO.setStoredFilePath(image.getStoredFilePath());
            });

            cafeteriaResponseDTOList.add(cafeteriaResponseDTO);
        }
        return cafeteriaResponseDTOList;
    }

    @Override
    public List<CafeteriaResponseDTO> getCafeteriasByNameAndCategory(String name, String category) {
        List<Cafeteria> cafeterias = cafeteriaRepository.findByCafeteriaNameContainingIgnoreCaseAndCafeteriaCategoryContainingIgnoreCase(name, category);

        List<CafeteriaResponseDTO> cafeteriaResponseDTOList = new ArrayList<>();

        // 각 Cafeteria 객체를 순회하며 Image 정보를 포함한 DTO를 생성
        for (Cafeteria cafeteria : cafeterias) {
            CafeteriaResponseDTO cafeteriaResponseDTO = new CafeteriaResponseDTO();
            cafeteriaResponseDTO.setCafeteriaId(cafeteria.getId());
            cafeteriaResponseDTO.setCafeteriaName(cafeteria.getCafeteriaName());
            cafeteriaResponseDTO.setCafeteriaCategory(cafeteria.getCafeteriaCategory());
            cafeteriaResponseDTO.setCafeteriaPhone(cafeteria.getCafeteriaPhone());
            cafeteriaResponseDTO.setCafeteriaAddress(cafeteria.getCafeteriaAddress());

            // 현재 Cafeteria의 ID에 해당하는 Image 객체를 조회
            Optional<Image> imageOptional = imageService.getCafeteriaImgByCafeteriaId(cafeteria.getId());

            // 이미지가 존재하는 경우 DTO에 이미지 정보를 설정
            imageOptional.ifPresent(image -> {
                cafeteriaResponseDTO.setStoredFilePath(image.getStoredFilePath());
            });

            cafeteriaResponseDTOList.add(cafeteriaResponseDTO);
        }
        return cafeteriaResponseDTOList;
    }

    @Override
    public CafeteriaDTO patchCafeteria(Long cafeteriaId, CafeteriaDTO cafeteriaDTO, MultipartFile file) {
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

        // 이미지 파일이 전달된 경우
        if (file != null) {
            // 기존 이미지 업데이트 또는 새 이미지 추가
            long imageId = imageService.addImage(cafeteria.getId(), file, false);
            if (imageId != -1) {
                cafeteria.setCafeteriaImageId(imageId);
            } else {
                throw new RuntimeException("Image upload failed");
            }
        }
        Cafeteria savedCafeteria = cafeteriaRepository.save(cafeteria);

        return CafeteriaMapper.mapToCafeteriaDto(savedCafeteria);
    }

    @Override
    public void deleteCafeteria(Long cafeteriaId) {
        String errMsg = String.format("Data does not exist for ID %s", cafeteriaId);
        Cafeteria cafeteria = cafeteriaRepository.findById(cafeteriaId)
                .orElseThrow(() -> new ResourceNotFoundException(errMsg, HttpStatus.NOT_FOUND));

        // 관련된 메뉴 데이터를 먼저 삭제
        menuRepository.deleteByCafeteriaId(cafeteriaId);

        // 그런 다음 식당 데이터를 삭제
        cafeteriaRepository.delete(cafeteria);
    }

    @Override
    public boolean isExistsCafeteria(Long cafeteriaId) {
        return cafeteriaRepository.existsById(cafeteriaId);
    }

    @Override
    @Transactional(readOnly = true)
    public CafeteriaResponseDTO getCafeteriaById(Long cafeteriaId) {
        // 예외 메시지 정의
        String errMsg = String.format("Data is not exists %s", cafeteriaId);

        // 식당 정보를 DB에서 조회
        Cafeteria cafeteria = cafeteriaRepository.findById(cafeteriaId)
                .orElseThrow(() -> new ResourceNotFoundException(errMsg, HttpStatus.NOT_FOUND));

        // 식당 이미지 정보를 가져옴
        Optional<Image> imageOptional = imageService.getCafeteriaImgByCafeteriaId(cafeteriaId);

        CafeteriaResponseDTO cafeteriaResponseDTO = new CafeteriaResponseDTO();

        cafeteriaResponseDTO.setCafeteriaId(cafeteria.getId());
        cafeteriaResponseDTO.setCafeteriaName(cafeteria.getCafeteriaName());
        cafeteriaResponseDTO.setCafeteriaCategory(cafeteria.getCafeteriaCategory());
        cafeteriaResponseDTO.setCafeteriaPhone(cafeteria.getCafeteriaPhone());
        cafeteriaResponseDTO.setCafeteriaAddress(cafeteria.getCafeteriaAddress());

        // Optional을 통해 이미지 정보가 존재할 경우 설정
        imageOptional.ifPresent(image -> {
            cafeteriaResponseDTO.setStoredFilePath(image.getStoredFilePath());
        });

        return cafeteriaResponseDTO;
    }

    @Override
    public CategoryResponseDTO getCategoryList() {
        // 모든 Cafeteria 엔터티를 가져옴
        List<Cafeteria> cafeteriaList = cafeteriaRepository.findAll();

        // 중복을 제거한 카테고리 리스트 생성
        List<String> categoryList = cafeteriaList.stream()
                .map(Cafeteria::getCafeteriaCategory) // Cafeteria 엔터티에서 카테고리 필드만 추출
                .distinct() // 중복된 카테고리를 제거
                .toList(); // 결과를 리스트로 수집

        // CategoryResponseDTO에 카테고리 리스트를 설정
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategory(categoryList); // 카테고리 리스트를 설정

        return categoryResponseDTO;
    }
}
