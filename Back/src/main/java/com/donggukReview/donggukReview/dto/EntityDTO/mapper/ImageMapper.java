package com.donggukReview.donggukReview.dto.EntityDTO.mapper;

import com.donggukReview.donggukReview.dto.EntityDTO.ImageDTO;
import com.donggukReview.donggukReview.entity.Image;

public class ImageMapper {

    public static Image mapToImage(ImageDTO imageDTO) {
        return  new Image(
                imageDTO.getId(),
                imageDTO.getStoredFilePath(),
                imageDTO.getIsUserImage(),
                imageDTO.getCreatorId()
        );
    }
    public static  ImageDTO mapToImageDTO(Image image) {
        return new ImageDTO(
                image.getId(),
                image.getCreatorId(),
                image.getStoredFilePath(),
                image.getIsUserImage()
        );
    }
}
