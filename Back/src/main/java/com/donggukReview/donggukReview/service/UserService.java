package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.dto.EntityDTO.CafeteriaDTO;
import com.donggukReview.donggukReview.dto.UserInfoResponseDTO;
import com.donggukReview.donggukReview.dto.UserLikeListResponseDTO;
import com.donggukReview.donggukReview.dto.UserLikeResponseDTO;
import com.donggukReview.donggukReview.entity.*;
import com.donggukReview.donggukReview.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CafeteriaService cafeteriaService;

    // 회원 가입
    public long register(Users userEntity, MultipartFile file) {
        try {
            // 유저 생성
            Users createdUserEntity = userRepository.save(userEntity);
            long imageId = imageService.addImage(createdUserEntity.getId(), file, true);

            if (imageId != -1) {
                createdUserEntity.setUserImageId(imageId);
            }
            return createdUserEntity.getId();
        } catch (Exception e) {
            return -1;
        }
    }

    public UserInfoResponseDTO getUserInfo(Users users) {
        Optional<Image> userImage = imageService.getImageById(users.getUserImageId());
        List<Likes> likesList = likeService.getLikeList(users.getId());
        List<Review> reviewList = reviewService.getAllReviewByUserId(users.getId());

        String userImagePath = "";
        userImagePath = userImage.map(Image::getStoredFilePath).orElse(null);

        UserInfoResponseDTO responseDTO = new UserInfoResponseDTO();
        responseDTO.setUserId(users.getUserId());
        responseDTO.setUserName(users.getUserNickname());
        responseDTO.setUserImagePath(userImagePath);
        responseDTO.setLikeNum(likesList.size());
        responseDTO.setReviewNum(reviewList.size());

        return responseDTO;
    }

    public UserLikeListResponseDTO getUserLikeList(Users users) {
        UserLikeListResponseDTO responseDTO = new UserLikeListResponseDTO();

        List<Likes> likeList = likeService.getLikeList(users.getId());
        List<UserLikeResponseDTO> responseDTOList = new ArrayList<>();

        for (Likes like : likeList) {
            Long cafeteriaId = like.getCafeteriaId();
            CafeteriaDTO cafeteriaDTO = cafeteriaService.getCafeteriaById(cafeteriaId);
            Optional<Image> cafeteriaImageOptional = imageService.getCafeteriaImgByCafeteriaId(cafeteriaId);

            String cafeteriaImagePath = "";
            cafeteriaImagePath = cafeteriaImageOptional.map(Image::getStoredFilePath).orElse(null);

            UserLikeResponseDTO userLikeResponseDTO = new UserLikeResponseDTO();
            userLikeResponseDTO.setCafeteriaId(cafeteriaId);
            userLikeResponseDTO.setCafeteriaName(cafeteriaDTO.getCafeteriaName());
            userLikeResponseDTO.setCafeteriaCategory(cafeteriaDTO.getCafeteriaCategory());
            userLikeResponseDTO.setCafeteriaImagePath(cafeteriaImagePath);

            responseDTOList.add(userLikeResponseDTO);
        }

        responseDTO.setUserLikeList(responseDTOList);
        return responseDTO;
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    };

    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    };
}