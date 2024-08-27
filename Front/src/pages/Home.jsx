import React, { useState } from 'react';
import Header from '../components/Header';
import CategoryDisplay from '../components/CategoryDisplay';
import LoginSection from '../components/LoginSection';
import SearchSection from '../components/SearchSection';
import UserProfile from '../components/UserProfile';

function Home() {
  const [loggedInUser, setLoggedInUser] = useState(null);

  const handleLogout = () => {
    localStorage.removeItem('token');
    setLoggedInUser(null);
  };

  return (
    <div>
      <Header />
      <div style={styles.container}>
        <div style={styles.section1}>
          <CategoryDisplay />
        </div>
        <div style={styles.section2}>
          <SearchSection />
        </div>
        <div style={styles.section3}>
          {loggedInUser ? (
            <UserProfile
              nickname={loggedInUser.nickname}
              image={loggedInUser.image}
              likes={loggedInUser.likes}
              reviews={loggedInUser.reviews}
              onLogout={handleLogout}
            />
          ) : (
            <LoginSection setLoggedInUser={setLoggedInUser} />
          )}
        </div>
      </div>
    </div>
  );
}


export default Home;
