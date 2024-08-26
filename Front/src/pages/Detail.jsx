import React from 'react';
import RestaurantDetail from '../components/RestaurantDetail';
import ReviewList from '../components/ReviewList';
import ReviewFormModal from '../components/ReviewFormModal';
import './Detail.css';

function Detail({ restaurant, reviews, isAdmin, isDeleteMode, onDelete, onSubmitReview }) {
  return (
    <div className="detail-page">
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
      {!isAdmin && <ReviewFormModal onSubmit={onSubmitReview} />}
      <ReviewList
        reviews={reviews}
        isAdmin={isAdmin}
        isDeleteMode={isDeleteMode}
        onDelete={onDelete}
      />
    </div>
  );
}

export default Detail;