import React, { useState } from 'react';
import ReviewForm from './ReviewForm';
import './ReviewFormModal.css';

function ReviewFormModal() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleReviewSubmit = (review) => {
    // 리뷰를 제출했을 때 모달을 닫고 나머지 로직 처리
    closeModal();
    console.log('Review submitted:', review);
    // 나중에 백엔드에 리뷰 데이터를 전송하는 로직을 추가
  };

  return (
    <div>
      <button onClick={openModal} className="open-modal-button">
        리뷰 작성하기
      </button>

      {isModalOpen && (
        <div className="modal-overlay">
          <div className="modal-content">
            <button className="close-button" onClick={closeModal}>
              &times;
            </button>
            <ReviewForm onSubmit={handleReviewSubmit} />
          </div>
        </div>
      )}
    </div>
  );
}

export default ReviewFormModal;
