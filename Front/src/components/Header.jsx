import React from 'react';
// import { Link } from 'react-router-dom';
import './Header.css';

function Header() {
  return (
    <header className="header">
      <div className="logo">동국대 맛집</div>
      <nav className="nav">
{/*         <Link href="/">홈</Link> */}
{/*         <Link href="/profile">내 정보</Link> */}
      </nav>
    </header>
  );
}

export default Header;
