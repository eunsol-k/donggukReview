import React from 'react';
import './Sidebar.css';

function Sidebar({ username, userId, likedStores, averageRating, isAdmin, onEditRestaurant, onDeleteReviews }) {
  return (
    <aside className="sidebar">
      <div className="profile-section">
        <div className="profile-pic">
          <img src="https://via.placeholder.com/60" alt="User" className="profile-img" />
        </div>
        <div className="profile-info">
          <p className="username">{username}</p>
          <p className="user-id">{userId}</p>
        </div>
      </div>
      <div className="user-stats">
        <p className="stat-item">좋아요 한 가게: {likedStores}개</p>
        <p className="stat-item">별점 매긴 가게 (평균 별점): {averageRating} ★</p>
      </div>
      {isAdmin && (
        <div className="admin-actions">
          <button className="edit-restaurant-button" onClick={onEditRestaurant}>
            음식점 정보 수정
          </button>
          <button className="delete-review-button" onClick={onDeleteReviews}>
            리뷰 삭제 모드
          </button>
        </div>
      )}
    </aside>
  );
}

export default Sidebar;
