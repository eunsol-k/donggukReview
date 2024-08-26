package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.dto.UserInfoResponseDTO;
import com.donggukReview.donggukReview.entity.Image;
import com.donggukReview.donggukReview.entity.Likes;
import com.donggukReview.donggukReview.entity.Review;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.repository.LikeRepository;
import com.donggukReview.donggukReview.repository.ReviewRepository;
import com.donggukReview.donggukReview.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ImageService imageService;

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
        List<Likes> likesList = likeRepository.findByUserId(users.getId());
        List<Review> reviewList = reviewService.getAllReviewByUserId(users.getId());

        String userImagePath = "";
        if (userImage.isPresent()) {
            userImagePath = userImage.get().getStoredFilePath();
        } else {
            userImagePath = null;
        }

        UserInfoResponseDTO responseDTO = new UserInfoResponseDTO();
        responseDTO.setUserId(users.getUserId());
        responseDTO.setUserName(users.getUserNickname());
        responseDTO.setUserImagePath(userImagePath);
        responseDTO.setLikeNum(likesList.size());
        responseDTO.setReviewNum(reviewList.size());

        return responseDTO;
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    };

    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    };

    public List<Likes> getLikesByUserId(long userId) {
        return likeRepository.findByUserId(userId);
    }
}