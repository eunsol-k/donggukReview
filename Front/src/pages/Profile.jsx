import React from 'react';
import ProfileSection from '../components/ProfileSection';
import './Profile.css';

function ProfilePage() {
  const userInfo = {
    username: '홍길동',
    userId: 'user123',
    profilePicture: 'https://via.placeholder.com/80',
  };

  return (
    <div className="profile-page">
      <ProfileSection userInfo={userInfo} />
      {/* 나머지 섹션은 이후 추가 */}
    </div>
  );
}

export default ProfilePage;