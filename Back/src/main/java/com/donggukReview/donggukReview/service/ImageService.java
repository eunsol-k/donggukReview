package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.common.FileUtils;
import com.donggukReview.donggukReview.entity.Image;
import com.donggukReview.donggukReview.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Slf4j
public class ImageService {
    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ImageRepository imageRepository;

    // 이미지 생성
    public long addImage(Long creatorId, MultipartFile file, boolean isUserImage) {
        Image imageEntity = new Image();
        try {
            imageEntity = fileUtils.parseImageInfo(creatorId, file, isUserImage);
        } catch(Exception e) {
            return -1;
        }

        if(imageEntity != null) {
            Image createdImageEntity = imageRepository.save(imageEntity);
            return createdImageEntity.getId();
        } else {
            return -1;
        }
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    // Image Entity 불러오기: 사용자 프로필 이미지
    public Optional<Image> getProfileByUserId(Long userId) {
        return imageRepository.findByCreatorIdAndIsUserImage(userId, true);
    }

    // Image Entity 불러오기: 음식점 대표 이미지
    public Optional<Image> getCafeteriaImgByCafeteriaId(Long cafeteriaId) {
        return imageRepository.findByCreatorIdAndIsUserImage(cafeteriaId, false);
    }

}
