import React from 'react';
import './Sidebar.css';

function Sidebar({ username, userId, likedStores, averageRating }) {
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
    </aside>
  );
}

export default Sidebar;
