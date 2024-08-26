-- 테이블이 존재할 시 제거
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS ratings;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cafeteria;

-- 음식점 테이블
CREATE TABLE cafeteria (
                           id BIGINT NOT NULL AUTO_INCREMENT, -- ID (Primary Key)
                           cafeteria_name VARCHAR(255), -- 음식점 이름
                           cafeteria_category VARCHAR(100), -- 음식점 카테고리
                           cafeteria_phone VARCHAR(100), -- 음식점 전화번호
                           cafeteria_address VARCHAR(255), -- 음식점 주소
                           cafeteria_image_id varchar(1000), -- 음식점 대표 이미지 ID
                           PRIMARY KEY (id)
) ENGINE=InnoDB;

-- 사용자 테이블
CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT, -- ID (Primary Key)
                        user_id VARCHAR(255) NOT NULL UNIQUE, -- 사용자 ID (Unique Key)
                        user_password VARCHAR(255), -- 사용자 비밀번호
                        user_nickname VARCHAR(100), -- 사용자 닉네임
                        user_image_id BIGINT, -- 사용자 프로필 이미지 ID (오타 수정),
                        user_role VARCHAR(100),
                        PRIMARY KEY (id)
) ENGINE=InnoDB;

-- 음식점 메뉴 테이블
CREATE TABLE menu (
                      id BIGINT NOT NULL AUTO_INCREMENT, -- ID (Primary Key)
                      cafeteria_id BIGINT, -- 음식점 ID
                      menu_name VARCHAR(100), -- 메뉴 이름
                      menu_price VARCHAR(100), -- 메뉴 가격
                      PRIMARY KEY (id),
                      CONSTRAINT fk_cafeteria FOREIGN KEY (cafeteria_id) REFERENCES cafeteria(id) -- 외래 키 제약 조건 추가
) ENGINE=InnoDB;

-- 음식점 평점 테이블
CREATE TABLE ratings (
                         id BIGINT NOT NULL AUTO_INCREMENT, -- ID (Primary Key)
                         cafeteria_id BIGINT, -- 음식점 ID
                         ratings_service VARCHAR(5), -- 서비스 평균 별점
                         ratings_price VARCHAR(5), -- 가격 평균 별점
                         ratings_flavor VARCHAR(5), -- 맛 평균 별점
                         ratings_total VARCHAR(5), -- 전체 평균 별점
                         PRIMARY KEY (id),
) ENGINE=InnoDB;

-- 리뷰 테이블
CREATE TABLE review (
                        id BIGINT NOT NULL AUTO_INCREMENT, -- ID (Primary Key)
                        user_id BIGINT, -- 리뷰 작성자 ID
                        cafeteria_id BIGINT, -- 리뷰 대상 음식점 ID
                        review_contents VARCHAR(1000), -- 리뷰 내용
                        review_ratings_service VARCHAR(5), -- 서비스 별점
                        review_ratings_price VARCHAR(5), -- 가격 별점
                        review_ratings_flavor VARCHAR(5), -- 맛 별점
                        review_ratings_total VARCHAR(5), -- 평균 별점
                        review_recommended INTEGER, -- 추천 수
                        PRIMARY KEY (id),
) ENGINE=InnoDB;

-- 좋아요 테이블
CREATE TABLE likes (
                       id BIGINT NOT NULL AUTO_INCREMENT, -- ID (Primary Key)
                       user_id BIGINT, -- 좋아요한 사용자 ID
                       cafeteria_id BIGINT, -- 좋아요 대상 음식점 ID
                       PRIMARY KEY (id),
) ENGINE=InnoDB;

-- 이미지 파일 테이블
CREATE TABLE image (
                       id BIGINT NOT NULL AUTO_INCREMENT, -- ID (Primary Key)
                       creator_id BIGINT, -- 사용자 ID 또는 음식점 ID
                       stored_file_path VARCHAR(255), -- 이미지 저장 경로
                       is_user_image BOOLEAN, -- 사용자 프로필 이미지인 경우 true, 음식점 대표 이미지인 경우 false
                       PRIMARY KEY (id),
) ENGINE=InnoDB;
