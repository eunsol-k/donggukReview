package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.common.FileUtils;
import com.donggukReview.donggukReview.entity.Image;
import com.donggukReview.donggukReview.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
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
}
