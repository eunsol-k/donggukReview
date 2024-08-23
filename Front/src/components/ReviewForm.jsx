import React, { useState } from 'react';
import './ReviewForm.css';

function ReviewForm({ onSubmit }) {
  const [overallRating, setOverallRating] = useState(0);
  const [serviceRating, setServiceRating] = useState(0);
  const [priceRating, setPriceRating] = useState(0);
  const [tasteRating, setTasteRating] = useState(0);
  const [waitingRating, setWaitingRating] = useState(0);
  const [reviewContent, setReviewContent] = useState('');

  const handleRatingChange = (ratingSetter, value) => {
    ratingSetter(value);
  };

  const renderStars = (rating, ratingSetter) => {
    return [...Array(5)].map((_, index) => {
      const fullValue = index + 1;
      const halfValue = index + 0.5;

      return (
        <span
          key={index}
          className={
            fullValue <= rating
              ? "star filled"
              : rating >= halfValue
              ? "star half-filled"
              : "star"
          }
          onClick={() =>
            handleRatingChange(
              ratingSetter,
              rating === halfValue ? fullValue : halfValue
            )
          }
        >
          ★
        </span>
      );
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const review = {
      overallRating,
      serviceRating,
      priceRating,
      tasteRating,
      waitingRating,
      reviewContent,
    };
    onSubmit(review);
  };

  return (
    <form className="review-form" onSubmit={handleSubmit}>
      <h2>리뷰 작성하기</h2>
      <div className="rating-group">
        <label>총평: </label>
        <div className="star-rating">
          {renderStars(overallRating, setOverallRating)}
        </div>
      </div>
      <div className="rating-group">
        <label>서비스: </label>
        <div className="star-rating">
          {renderStars(serviceRating, setServiceRating)}
        </div>
      </div>
      <div className="rating-group">
        <label>가격: </label>
        <div className="star-rating">
          {renderStars(priceRating, setPriceRating)}
        </div>
      </div>
      <div className="rating-group">
        <label>음식 맛: </label>
        <div className="star-rating">
          {renderStars(tasteRating, setTasteRating)}
        </div>
      </div>
      <div className="rating-group">
        <label>웨이팅: </label>
        <div className="star-rating">
          {renderStars(waitingRating, setWaitingRating)}
        </div>
      </div>
      <div className="review-content">
        <label>리뷰 내용: </label>
        <textarea value={reviewContent} onChange={(e) => setReviewContent(e.target.value)} />
      </div>
      <button type="submit">리뷰 등록</button>
    </form>
  );
}

export default ReviewForm;
