import React from 'react';
import './Profile.css'; // CSS 파일도 동일하게 변경

function ProfilePage({ user }) {
  return (
    <div className="profile-page-container">
      <h1>My Page</h1>
      <div className="profile-section">
        <img src={user.profilePicture} alt="Profile" className="profile-img" />
        <div className="profile-info">
          <h2>{user.username}</h2>
          <p>ID: {user.userId}</p>
          <p>Email: {user.email}</p>
        </div>
      </div>
      <div className="activity-section">
        <h3>My Reviews</h3>
        <ul>
          {user.reviews.map((review, index) => (
            <li key={index}>{review.content} - {review.date}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default ProfilePage;
