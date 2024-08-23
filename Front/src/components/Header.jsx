import React from 'react';
import './Header.css';

function Header() {
  return (
    <header className="header">
      <div className="logo">동국대 맛집</div>
      <nav className="nav">
        <a href="/">홈</a>
        <a href="/profile">내 정보</a>
      </nav>
    </header>
  );
}

export default Header;
