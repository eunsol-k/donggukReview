import React, { useState, useEffect } from 'react';
import ProfileSection from '../components/ProfileSection';
import './Profile.css';

function ProfilePage() {
  const [userInfo, setUserInfo] = useState({
    username: '홍길동',
    userId: 'user123',
    profilePicture: 'https://via.placeholder.com/80',
  });

  const [likedRestaurants, setLikedRestaurants] = useState([]);
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    fetchUserData();
  }, []);

  const fetchUserData = async () => {
    const exampleLikedRestaurants = [
      { name: 'Restaurant 1', category: ['카페'] },
      { name: 'Restaurant 2', category: ['한식', '퓨전'] },
    ];

    const exampleReviews = [
      {
        restaurant: 'Restaurant 1',
        content: '정말 맛있어요!',
        date: '2023-08-01',
      },
      {
        restaurant: 'Restaurant 2',
        content: '서비스가 정말 좋았습니다!',
        date: '2023-08-02',
      },
    ];

    setLikedRestaurants(exampleLikedRestaurants);
    setReviews(exampleReviews);
  };

  return (
    <div className="profile-page">
      <ProfileSection userInfo={userInfo} />

      <div className="profile-section liked-restaurants-section">
        <h3>좋아요 한 음식점</h3>
        <ul>
          {likedRestaurants.map((restaurant, index) => (
            <li key={index}>
              {restaurant.name} - {restaurant.category.join(', ')}
            </li>
          ))}
        </ul>
      </div>

      <div className="profile-section reviews-section">
        <h3>작성한 댓글</h3>
        <ul>
          {reviews.map((review, index) => (
            <li key={index}>
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

export default ProfilePage;
