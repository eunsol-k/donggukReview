import React, { useState } from 'react';

function RestaurantEditForm({ restaurant, onSave }) {
  const [updatedRestaurant, setUpdatedRestaurant] = useState(restaurant || {
    name: '',
    phone: '',
    address: '',
    category: [],
    menu: [],
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedRestaurant((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // 기존 메뉴 항목을 수정하는 핸들러
  const handleMenuChange = (index, field, value) => {
    const updatedMenu = [...updatedRestaurant.menu];
    updatedMenu[index] = {
      ...updatedMenu[index],
      [field]: value,
    };
    setUpdatedRestaurant((prev) => ({
      ...prev,
      menu: updatedMenu,
    }));
  };

  // 새로운 메뉴 항목을 추가하는 핸들러
  const handleAddMenu = () => {
    setUpdatedRestaurant((prev) => ({
      ...prev,
      menu: [...prev.menu, { name: '', price: '' }],
    }));
  };

  // 메뉴 항목을 삭제하는 핸들러
  const handleDeleteMenu = (index) => {
    const updatedMenu = updatedRestaurant.menu.filter((_, i) => i !== index);
    setUpdatedRestaurant((prev) => ({
      ...prev,
      menu: updatedMenu,
    }));
  };

  const handleSave = () => {
    onSave(updatedRestaurant);
    // 저장 후 모드 종료 등을 처리하려면 이곳에서 추가적으로 콜백을 호출할 수 있습니다.
  };

  return (
    <div className="restaurant-edit-form">
      <h2>음식점 정보 수정</h2>
      <div>
        <label>이름:</label>
        <input
          type="text"
          name="name"
          value={updatedRestaurant.name}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>전화번호:</label>
        <input
          type="text"
          name="phone"
          value={updatedRestaurant.phone}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>주소:</label>
        <input
          type="text"
          name="address"
          value={updatedRestaurant.address}
          onChange={handleChange}
        />
      </div>
      {/* 카테고리나 메뉴를 추가로 수정할 수 있는 입력 필드 */}
      <div>
        <label>카테고리:</label>
        <input
          type="text"
          name="category"
          value={updatedRestaurant.category?.join(', ')}
          onChange={(e) =>
            setUpdatedRestaurant((prev) => ({
              ...prev,
              category: e.target.value.split(',').map((cat) => cat.trim()),
            }))
          }
        />
      </div>
      <div>
        <label>메뉴:</label>
        {updatedRestaurant.menu?.map((item, index) => (
          <div key={index} style={{ display: 'flex', alignItems: 'center' }}>
            <input
              type="text"
              name={`menu-name-${index}`}
              placeholder="메뉴 이름"
              value={item.name}
              onChange={(e) => handleMenuChange(index, 'name', e.target.value)}
            />
            <input
              type="text"
              name={`menu-price-${index}`}
              placeholder="가격"
              value={item.price}
              onChange={(e) => handleMenuChange(index, 'price', e.target.value)}
            />
            <button
              type="button"
              onClick={() => handleDeleteMenu(index)}
              style={{ marginLeft: '10px' }}
            >
              삭제
            </button>
          </div>
        ))}
        <button onClick={handleAddMenu}>메뉴 추가</button> {/* 메뉴 추가 버튼 */}
      </div>
      <button onClick={handleSave}>저장</button>
    </div>
  );
}

export default RestaurantEditForm;
