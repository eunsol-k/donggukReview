import React, { useState, useEffect } from 'react';
import Header from './components/Header';
import RestaurantDetail from './components/RestaurantDetail';
import ReviewList from './components/ReviewList';
import Sidebar from './components/Sidebar';
import './App.css';

function App() {
  const [username, setUsername] = useState('홍길동');
  const [userId, setUserId] = useState('user123');
  const [likedStores, setLikedStores] = useState(5);
  const [averageRating, setAverageRating] = useState(4.3);
  const [searchTerm, setSearchTerm] = useState('');

  const [restaurant, setRestaurant] = useState({
    name: 'Restaurant 1',
    phone: '123-456-7890',
    location: 'Seoul',
    rating: 4.5,
    category: ['카페', '디저트'],
    reviewCount: 43,
    score: 88,
    address: '서울특별시 강남구 삼성동 123',
    features: ['무료 Wi-Fi', '반려동물 출입 가능'],
    operatingHours: '09:00 - 22:00',
    image: 'https://via.placeholder.com/400x300',
  });

  const [reviews, setReviews] = useState([
    {
      username: 'User1',
      rating: 4,
      content: '맛있어요!',
      date: '2023-08-01',
    },
    {
      username: 'User2',
      rating: 5,
      content: '정말 훌륭한 서비스였습니다!',
      date: '2023-08-02',
    },
  ]);

  return (
    <div className="app-container">
      <Header />
      <div className="content">
        <div className="left-section">
          {/* 왼쪽 섹션을 빈 공간으로 유지 */}
        </div>
        <div className="middle-section">
          <input
            type="text"
            placeholder="음식점 이름을 검색하세요..."
            className="search-bar"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <RestaurantDetail
            name={restaurant.name}
            category={restaurant.category}
            rating={restaurant.rating}
            reviewCount={restaurant.reviewCount}
            score={restaurant.score}
            address={restaurant.address}
            phone={restaurant.phone}
            features={restaurant.features}
            operatingHours={restaurant.operatingHours}
            image={restaurant.image}
          />
          <ReviewList reviews={reviews} />
        </div>
        <div className="right-section">
          <Sidebar
            username={username}
            userId={userId}
            likedStores={likedStores}
            averageRating={averageRating}
          />
        </div>
      </div>
    </div>
  );
}

export default App;
