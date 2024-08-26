import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import Sidebar from './components/Sidebar';
import Home from './pages/Home';
import Detail from './pages/Detail';
import Profile from './pages/Profile';
import RestaurantEditForm from './components/RestaurantEditForm';
import './App.css';

function App() {
  const [isEditingRestaurant, setIsEditingRestaurant] = useState(false); // 수정 모드 상태
  const [isAdmin, setIsAdmin] = useState(true); // 관리자 여부
  const [isDeleteMode, setIsDeleteMode] = useState(false); // 삭제 모드 상태

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
    setIsEditingRestaurant(!isEditingRestaurant); // 수정 모드 전환
  };

  const handleRestaurantSave = (updatedRestaurant) => {
    setRestaurant(updatedRestaurant); // 수정된 음식점 정보로 상태 업데이트
    setIsEditingRestaurant(false); // 수정 모드 종료
  };

  const handleDeleteReview = (userId) => {
    setReviews(reviews.filter((review) => review.userId !== userId)); // 리뷰 삭제
  };

  const handleDeleteReviews = () => {
    setIsDeleteMode(!isDeleteMode); // 삭제 모드 전환
  };

  const handleReviewSubmit = (newReview) => {
    const reviewWithUserInfo = {
      ...newReview,
      userId: userInfo.userId,
      date: new Date().toISOString().split('T')[0],
    };
    setReviews([...reviews, reviewWithUserInfo]); // 새로운 리뷰 추가
  };

  const toggleAdminMode = () => {
    setIsAdmin(!isAdmin); // 관리자 모드 전환
  };

  return (
    <Router>
      <div className="app-container">
        <Header />
        <div className="content">
          <div className="left-section">
{/*             <p>Left Section</p> */}
          </div>
          <div className="middle-section">
            <Routes>
              <Route
                path="/"
                element={<Home />}
              />
              <Route
                path="/details"
                element={
                  isEditingRestaurant ? (
                    <RestaurantEditForm
                      restaurant={restaurant}
                      onSave={handleRestaurantSave}
                    />
                  ) : (
                    <Detail
                      restaurant={restaurant}
                      reviews={reviews}
                      isAdmin={isAdmin}
                      isDeleteMode={isDeleteMode}
                      onDelete={handleDeleteReview}
                      onSubmitReview={handleReviewSubmit}
                    />
                  )
                }
              />
              <Route
                path="/profile"
                element={<Profile />}
              />
            </Routes>
          </div>
          <div className="right-section">
            {/* Profile 페이지가 아닌 경우에만 Sidebar를 렌더링 */}
            <Routes>
              <Route
                path="/profile"
                element={null} // Profile 페이지에서는 Sidebar를 숨김
              />
              <Route
                path="*"
                element={
                  <>
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
                  </>
                }
              />
            </Routes>
          </div>
        </div>
      </div>
    </Router>
  );
}

export default App;
