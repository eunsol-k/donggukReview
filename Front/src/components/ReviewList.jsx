import React, { useState } from 'react';
import './ReviewList.css';

function ReviewList({ reviews }) {
  const [visibleCount, setVisibleCount] = useState(3);
  const [isExpanded, setIsExpanded] = useState(false);

  const showMoreReviews = () => {
    setVisibleCount(reviews.length);
    setIsExpanded(true);
  };

  const showLessReviews = () => {
    setVisibleCount(3);
    setIsExpanded(false);
  };

  return (
    <div className="review-list">
      <h2>리뷰</h2>
      {reviews.slice(0, visibleCount).map((review, index) => (
        <div key={index} className="review-item">
          <div className="review-header">
            <div className="review-profile">
              {review.profilePicture ? (
                <img src={review.profilePicture} alt="프로필 사진" className="profile-img" />
              ) : (
                <div className="profile-placeholder">프로필</div>
              )}
            </div>
            <div className="review-author">
              <span className="review-username">{review.username}</span>
              <span className="review-userid">({review.userId})</span>
            </div>
          </div>
          <p className="review-date">{review.date}</p>
          <p className="review-content">{review.content}</p>
          {review.photos && review.photos.length > 0 && (
            <div className="review-photos">
              {review.photos.map((photo, idx) => (
                <img key={idx} src={photo} alt={`리뷰 사진 ${idx + 1}`} className="review-photo" />
              ))}
            </div>
          )}
          <div className="review-ratings">
            <p>총 평균 별점: {review.overallRating} ★</p>
            <p>서비스 별점: {review.serviceRating} ★</p>
            <p>가격 별점: {review.priceRating} ★</p>
            <p>음식 맛 별점: {review.tasteRating} ★</p>
            <p>웨이팅 별점: {review.waitingRating} ★</p>
          </div>
        </div>
      ))}
      {!isExpanded && visibleCount < reviews.length && (
        <button className="toggle-button" onClick={showMoreReviews}>
          더보기
        </button>
      )}
      {isExpanded && (
        <button className="toggle-button" onClick={showLessReviews}>
          축소
        </button>
      )}
    </div>
  );
}

export default ReviewList;
