package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.dto.RegisterRequestDto;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원 가입
    public long register(RegisterRequestDto requestDto, MultipartFile file) {
        Users userEntity = new ModelMapper().map(requestDto, Users.class);
        userEntity.setUserPassword(bCryptPasswordEncoder.encode(userEntity.getUserPassword()));
        userEntity.setUserRole("ROLE_USER");

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
