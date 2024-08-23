import React, { useState, useEffect } from 'react';
import './RestaurantDetail.css';

function RestaurantDetail({
  name,
  category = [],
  reviews = [],
  address,
  phone,
  image,
  menu = [],
}) {
  const [isFavorite, setIsFavorite] = useState(false);
  const [averageRating, setAverageRating] = useState(0);

  // useEffect 훅을 사용해 백엔드에서 데이터를 가져올 때 필요한 설정을 여기에 추가
  useEffect(() => {
    // 백엔드에서 리뷰 데이터를 가져온 후, 총점을 계산하여 상태로 설정
    if (reviews.length > 0) {
      const totalRating = reviews.reduce((acc, review) => acc + review.overallRating, 0);
      setAverageRating(totalRating / reviews.length);
    }
    // 이 예시에서는 reviews를 props로 받았지만,
    // 실제 구현에서는 API 요청을 통해 백엔드에서 데이터를 가져와야 함
    // fetchData(); // 백엔드에서 데이터를 가져오는 함수를 여기에 추가
  }, [reviews]);

  const toggleFavorite = () => {
    setIsFavorite(!isFavorite);
  };

  const renderStars = () => {
    const stars = [];
    const fullStars = Math.floor(averageRating);
    const hasHalfStar = averageRating - fullStars >= 0.5;

    for (let i = 1; i <= 5; i++) {
      if (i <= fullStars) {
        stars.push(<span key={i} className="filled-star">★</span>);
      } else if (i === fullStars + 1 && hasHalfStar) {
        stars.push(<span key={i} className="half-filled-star">★</span>);
      } else {
        stars.push(<span key={i} className="empty-star">★</span>);
      }
    }

    return stars;
  };

  return (
    <div className="restaurant-detail">
      <div className="restaurant-header">
        <h1 className="restaurant-name">{name}</h1>
        <button className={`favorite-button ${isFavorite ? 'active' : ''}`} onClick={toggleFavorite}>
          ♥
        </button>
      </div>
      <div className="restaurant-images">
        <img src={image} alt={name} className="main-image" />
      </div>
      <div className="restaurant-info">
        <p className="restaurant-category">{category.join(', ')}</p>
        <div className="restaurant-rating">
          <div className="rating-stars">{renderStars()}</div>
          <span className="rating-score">{averageRating.toFixed(1)}</span>
          <span className="review-count">({reviews.length}명의 평가)</span>
        </div>
        <div className="restaurant-meta">
          <p className="address">{address}</p>
          <p className="phone">{phone}</p>
        </div>
        <div className="restaurant-menu">
          <h2>메뉴</h2>
          <ul>
            {menu.length > 0 ? (
              menu.map((item, index) => (
                <li key={index}>
                  <span className="menu-item-name">{item.name}</span>
                  <span className="menu-item-price">{item.price}원</span>
                </li>
              ))
            ) : (
              <p>메뉴 정보가 없습니다.</p>
            )}
          </ul>
        </div>
      </div>
    </div>
  );
}

export default RestaurantDetail;
