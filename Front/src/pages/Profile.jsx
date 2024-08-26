import React, { useState, useEffect } from 'react';
import './Profile.css';

function Profile() {  // 컴포넌트 이름을 Profile로 변경
  const [userInfo, setUserInfo] = useState({
    username: '홍길동',
    userId: 'user123',
    profilePicture: 'https://via.placeholder.com/60',
  });

  const [likedRestaurants, setLikedRestaurants] = useState([
    { id: 1, name: 'Restaurant 1', category: '카페' },
    { id: 2, name: 'Restaurant 2', category: '한식' },
  ]);

  const [reviews, setReviews] = useState([
    { id: 1, restaurant: 'Restaurant 1', content: '맛있어요!', date: '2023-08-01' },
    { id: 2, restaurant: 'Restaurant 2', content: '정말 훌륭한 서비스였습니다!', date: '2023-08-02' },
  ]);

  useEffect(() => {
    // 실제로는 API 호출 등을 통해 사용자 정보, 좋아요한 음식점, 리뷰 리스트를 가져옵니다.
  }, []);

  return (
    <div className="profile-page">
      <div className="profile-header">
        <img src={userInfo.profilePicture} alt="프로필 사진" className="profile-img" />
        <div className="profile-info">
          <h2>{userInfo.username}</h2>
          <p>ID: {userInfo.userId}</p>
        </div>
      </div>
      <div className="profile-section">
        <h3>좋아요 한 음식점</h3>
        <ul>
          {likedRestaurants.map((restaurant) => (
            <li key={restaurant.id}>{restaurant.name} - {restaurant.category}</li>
          ))}
        </ul>
      </div>
      <div className="profile-section">
        <h3>작성한 리뷰</h3>
        <ul>
          {reviews.map((review) => (
            <li key={review.id}>
              <p><strong>{review.restaurant}</strong></p>
              <p>{review.content}</p>
              <p>{review.date}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default Profile;  // Profile로 이름을 변경하여 내보내기
