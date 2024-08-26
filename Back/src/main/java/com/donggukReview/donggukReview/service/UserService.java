package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

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

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    };

    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    };
}