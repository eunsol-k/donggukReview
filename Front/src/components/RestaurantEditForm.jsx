import React, { useState } from 'react';

function RestaurantEditForm({ restaurant, onSave }) {
  const [updatedRestaurant, setUpdatedRestaurant] = useState(restaurant || {
    name: '',
    phone: '',
    address: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedRestaurant((prev) => ({
      ...prev,
      [name]: value,
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
          <div key={index}>
            <input
              type="text"
              name={`menu-name-${index}`}
              value={item.name}
              onChange={(e) =>
                setUpdatedRestaurant((prev) => {
                  const newMenu = [...prev.menu];
                  newMenu[index].name = e.target.value;
                  return { ...prev, menu: newMenu };
                })
              }
            />
            <input
              type="text"
              name={`menu-price-${index}`}
              value={item.price}
              onChange={(e) =>
                setUpdatedRestaurant((prev) => {
                  const newMenu = [...prev.menu];
                  newMenu[index].price = e.target.value;
                  return { ...prev, menu: newMenu };
                })
              }
            />
          </div>
        ))}
      </div>
      <button onClick={handleSave}>저장</button>
    </div>
  );
}

export default RestaurantEditForm;
