import React from 'react';
import './ReviewList.css';

function ReviewList({ reviews }) {
  return (
    <div className="review-list">
      <h2>리뷰</h2>
      {reviews.length > 0 ? (
        reviews.map((review, index) => (
          <div key={index} className="review-item">
            <div className="review-user">
              <span className="review-username">{review.username}</span>
              <span className="review-rating">★ {review.rating}</span>
            </div>
            <p className="review-content">{review.content}</p>
            <p className="review-date">{review.date}</p>
          </div>
        ))
      ) : (
        <p>리뷰가 없습니다.</p>
      )}
    </div>
  );
}

export default ReviewList;