import React, { useState } from 'react';
import './ReviewForm.css';

function ReviewForm({ onSubmit }) {
  const [overallRating, setOverallRating] = useState(0);
  const [serviceRating, setServiceRating] = useState(0);
  const [priceRating, setPriceRating] = useState(0);
  const [tasteRating, setTasteRating] = useState(0);
  const [reviewContent, setReviewContent] = useState('');

  const handleRatingChange = (ratingSetter, value, currentRating) => {
    if (currentRating >= value) {
      ratingSetter(value - 0.5);  // 반개로 변경
    } else {
      ratingSetter(value);  // 한 개로 변경
    }
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
              rating === halfValue ? fullValue : halfValue,
              rating
            )
          }
        >
          ★
        </span>
      );
    });
  };

  const handleImageUpload = (e) => {
    const uploadedImages = Array.from(e.target.files);
    setImages([...images, ...uploadedImages]);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // 리뷰 데이터를 객체로 생성
    const review = {
      overallRating,
      serviceRating,
      priceRating,
      tasteRating,
      reviewContent,
    };

    // 여기에 백엔드에 데이터를 전송하는 코드 추가
    // 예: POST 요청을 사용하여 서버로 리뷰 데이터를 전송
    // fetch('/api/reviews', {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify(review),
    // })
    // .then(response => response.json())
    // .then(data => {
    //   console.log('Review submitted:', data);
    //   // 성공적으로 제출된 후 추가 작업 (예: 폼 리셋)
    // })
    // .catch((error) => {
    //   console.error('Error:', error);
    // });

    onSubmit(review); // 이 부분은 백엔드 연동 시 대체될 수 있음
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
      <div className="review-content">
        <textarea
          placeholder="리뷰를 적어주세요"  // 플레이스홀더 추가
          value={reviewContent}
          onChange={(e) => setReviewContent(e.target.value)}
        />
      </div>
      <div className="submit-button">
        <button type="submit">리뷰 등록</button>
      </div>
    </form>
  );
}

export default ReviewForm;
