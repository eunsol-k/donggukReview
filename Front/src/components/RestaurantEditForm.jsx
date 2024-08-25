import React, { useState } from 'react';

function RestaurantEditForm({ restaurant, onSave }) {
  const [updatedRestaurant, setUpdatedRestaurant] = useState(restaurant);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedRestaurant((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSave = () => {
    onSave(updatedRestaurant);
  };

  return (
    <div className="restaurant-edit-form">
      <h2>음식점 정보 수정</h2>
      <div>
        <label>이름:</label>
        <input type="text" name="name" value={updatedRestaurant.name} onChange={handleChange} />
      </div>
      <div>
        <label>전화번호:</label>
        <input type="text" name="phone" value={updatedRestaurant.phone} onChange={handleChange} />
      </div>
      <div>
        <label>주소:</label>
        <input type="text" name="address" value={updatedRestaurant.address} onChange={handleChange} />
      </div>
      <button onClick={handleSave}>저장</button>
    </div>
  );
}

export default RestaurantEditForm;
