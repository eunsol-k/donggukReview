package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.entity.Likes;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.repository.LikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public List<Likes> getLikeList(long userId) {
        return likeRepository.findByUserId(userId);
    }

    public void checkLike(Users users, long cafeteriaId) {
        Likes likes = new Likes();
        likes.setUserId(users.getId());
        likes.setCafeteriaId(cafeteriaId);
        likeRepository.save(likes);
    }

    public void unCheckLike(Users users, long cafeteriaId) {
        Likes likes = likeRepository.findByUserIdAndCafeteriaId(users.getId(), cafeteriaId);
        likeRepository.delete(likes);
    }

    public boolean isExistsLike(Users users, long cafeteriaId) {
        return likeRepository.existsByUserIdAndCafeteriaId(users.getId(), cafeteriaId);
    }
}
