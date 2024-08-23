import React from 'react';
import './RestaurantDetail.css';

function RestaurantDetail({
  name,
  category,
  rating,
  reviewCount,
  score,
  address,
  phone,
//   features,
//   operatingHours,
  image,  // 이미지 하나만 사용
}) {
  return (
    <div className="restaurant-detail">
      <div className="restaurant-images">
        <img src={image} alt={name} className="main-image" />
      </div>
      <div className="restaurant-info">
        <h1 className="restaurant-name">{name}</h1>
        <p className="restaurant-category">{category.join(', ')}</p>
        <div className="restaurant-rating">
          <span className="rating-stars">★ ★ ★ ★ ☆</span>
          <span className="rating-score">{rating.toFixed(1)}</span>
          <span className="review-count">({reviewCount}명의 평가)</span>
          <span className="score">{score}점</span>
        </div>
        <div className="restaurant-meta">
          <p className="address">{address}</p>
          <p className="phone">{phone}</p>
{/*           <p className="features">{features.join(', ')}</p> */}
{/*           <p className="operating-hours">{operatingHours}</p> */}
        </div>
      </div>
    </div>
  );
}

export default RestaurantDetail;
