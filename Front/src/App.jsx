import React, { useState } from 'react';
import Header from './components/Header';
import RestaurantDetail from './components/RestaurantDetail';
import ReviewList from './components/ReviewList';
import Sidebar from './components/Sidebar';
import RestaurantEditForm from './components/RestaurantEditForm';
import ReviewForm from './components/ReviewFormModal'; // 리뷰 작성 폼 임포트
import './App.css';

function App() {
  const [isEditingRestaurant, setIsEditingRestaurant] = useState(false);
  const [isAdmin, setIsAdmin] = useState(true); // 관리자 여부
  const [isDeleteMode, setIsDeleteMode] = useState(false); // 삭제 모드 상태 추가

  const userInfo = isAdmin
    ? { username: '관리자', userId: 'admin123' }
    : { username: '홍길동', userId: 'user123' };

  const [restaurant, setRestaurant] = useState({
    name: 'Restaurant 1',
    phone: '123-456-7890',
    location: 'Seoul',
    rating: 4.5,
    category: ['카페', '디저트'],
    reviewCount: 43,
    address: '서울특별시 강남구 삼성동 123',
    image: 'https://via.placeholder.com/400x300',
    menu: [
      { name: '아메리카노', price: 4500 },
      { name: '카페라떼', price: 5000 },
      { name: '치즈케이크', price: 7000 },
    ],
  });

  const [reviews, setReviews] = useState([
    {
      username: 'User1',
      userId: 'user1',
      profilePicture: 'https://via.placeholder.com/50',
      date: '2023-08-01',
      content: '맛있어요!',
      photos: ['https://via.placeholder.com/100', 'https://via.placeholder.com/100'],
      overallRating: 4.5,
      serviceRating: 4,
      priceRating: 5,
      tasteRating: 4.5,
    },
    {
      username: 'User2',
      userId: 'user2',
      profilePicture: null,
      date: '2023-08-02',
      content: '정말 훌륭한 서비스였습니다!',
      photos: [],
      overallRating: 4,
      serviceRating: 4,
      priceRating: 3.5,
      tasteRating: 4,
    },
  ]);

  const handleEditRestaurant = () => {
    setIsEditingRestaurant(!isEditingRestaurant);
  };

  const handleRestaurantSave = (updatedRestaurant) => {
    setRestaurant(updatedRestaurant);
    setIsEditingRestaurant(false);
  };

  const handleDeleteReview = (userId) => {
    setReviews(reviews.filter((review) => review.userId !== userId));
  };

  const handleDeleteReviews = () => {
    setIsDeleteMode(!isDeleteMode);
  };

  const handleReviewSubmit = (newReview) => {
    const reviewWithUserInfo = {
      ...newReview,
      userId: userInfo.userId,
      date: new Date().toISOString().split('T')[0],
    };
    setReviews([...reviews, reviewWithUserInfo]);
  };

  const toggleAdminMode = () => {
    setIsAdmin(!isAdmin);
  };

  return (
    <div className="app-container">
      <Header />
      <div className="content">
        {/* 왼쪽 섹션 추가 */}
        <div className="left-section">
          {/* 이 부분에 필요한 내용을 추가할 수 있습니다 */}
          <p>Left Section</p>
        </div>
        <div className="middle-section">
          <input
            type="text"
            placeholder="음식점 이름을 검색하세요..."
            className="search-bar"
          />
          {!isEditingRestaurant ? (
            <RestaurantDetail
              name={restaurant.name}
              category={restaurant.category}
              rating={restaurant.rating}
              reviewCount={restaurant.reviewCount}
              address={restaurant.address}
              phone={restaurant.phone}
              image={restaurant.image}
              menu={restaurant.menu}
            />
          ) : (
            <RestaurantEditForm
              restaurant={restaurant}
              onSave={handleRestaurantSave}
            />
          )}
          {!isAdmin && <ReviewForm onSubmit={handleReviewSubmit} />}
          <ReviewList reviews={reviews} isAdmin={isAdmin} isDeleteMode={isDeleteMode} onDelete={handleDeleteReview} />
        </div>
        <div className="right-section">
          <Sidebar
            username={userInfo.username}
            userId={userInfo.userId}
            likedStores={5}
            averageRating={4.3}
            isAdmin={isAdmin}
            isEditingRestaurant={isEditingRestaurant}
            isDeleteMode={isDeleteMode}
            onEditRestaurant={handleEditRestaurant}
            onDeleteReviews={handleDeleteReviews}
          />
          <button onClick={toggleAdminMode} className="toggle-admin-button">
            {isAdmin ? '일반 모드로 전환' : '관리자 모드로 전환'}
          </button>
        </div>
      </div>
    </div>
  );
}

export default App;