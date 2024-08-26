package com.donggukReview.donggukReview.service.impl;

import com.donggukReview.donggukReview.dto.CafeteriaDetailResponseDTO;
import com.donggukReview.donggukReview.dto.CafeteriaResponseDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.CafeteriaDTO;
import com.donggukReview.donggukReview.dto.EntityDTO.mapper.CafeteriaMapper;
import com.donggukReview.donggukReview.dto.ReviewResponseDTO;
import com.donggukReview.donggukReview.entity.Cafeteria;
import com.donggukReview.donggukReview.entity.Image;
import com.donggukReview.donggukReview.entity.Review;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.exception.ResourceNotFoundException;
import com.donggukReview.donggukReview.repository.CafeteriaRepository;
import com.donggukReview.donggukReview.repository.ImageRepository;
import com.donggukReview.donggukReview.repository.ReviewRepository;
import com.donggukReview.donggukReview.service.CafeteriaService;
import com.donggukReview.donggukReview.service.ImageService;
import com.donggukReview.donggukReview.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class CafeteriaServiceImpl implements CafeteriaService {

    private final CafeteriaRepository cafeteriaRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final ReviewRepository reviewRepository;
    private final LikeService likeService;

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
        imageOptional.ifPresent(image -> {
            cafeteriaResponseDTO.setStoredFilePath(image.getStoredFilePath());
        });

        // 리뷰 목록 가져오기
        List<Review> reviewList = reviewRepository.findByCafeteriaId(cafeteriaId);
        List<ReviewResponseDTO> reviewResponseDTOList = reviewList.stream()
                .map(review -> {
                    ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
                    reviewResponseDTO.setReviewContents(review.getReviewContents());
                    reviewResponseDTO.setReviewRatingsTotal(review.getReviewRatingsTotal());
                    reviewResponseDTO.setReviewRecommended(review.getReviewRecommended());
                    return reviewResponseDTO;
                }).toList();

        // CafeteriaDetailResponseDTO 생성 및 설정
        CafeteriaDetailResponseDTO cafeteriaDetailResponseDTO = new CafeteriaDetailResponseDTO();
        cafeteriaDetailResponseDTO.setLike(likeService.isExistsLike(users, cafeteriaId));
        cafeteriaDetailResponseDTO.setCafeteriaResponseDTOList(List.of(cafeteriaResponseDTO));
        cafeteriaDetailResponseDTO.setReviewResponseDTOList(reviewResponseDTOList);

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
        List<Cafeteria> cafeterias =  cafeteriaRepository.findByCafeteriaNameContainingIgnoreCaseAndCafeteriaCategoryContainingIgnoreCase(name, category);

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
        if(file != null) {
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
        String errMsg = String.format("Data is not exists %s", cafeteriaId);
        Cafeteria cafeteria = cafeteriaRepository.findById(cafeteriaId)
                .orElseThrow(() -> new ResourceNotFoundException(errMsg, HttpStatus.NOT_FOUND));
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
}
