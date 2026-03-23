# DAEWON SHOP

Spring Boot 기반의 상품 옵션(단일/멀티팩/세트) 구조를 지원하는 쇼핑몰 프로젝트입니다.

## 🔥 주요 기능

### 👤 사용자
- 회원가입 / 로그인 (Spring Security)
- 마이페이지
  - 배송지 관리 (추가 / 목록)
  - 카카오 주소 검색 API 적용

### 🛍️ 상품
- 상품 등록 (옵션 구조 포함)
- 옵션 타입
  - SINGLE
  - MULTI_PACK
  - SET

### 🧺 장바구니
- 상품 + 옵션 단위 저장
- cartItem / cartItemDetail 구조로 옵션 분리 관리

### 📦 주문
- 장바구니 기반 주문 생성
- 주문 아이템 분리 저장 (order / order_item)

## 🛠️ 기술 스택

- Backend: Spring Boot, Spring Security, JPA (Hibernate)
- Frontend: Thymeleaf, Vanilla JS
- Database: MySQL
- 기타:
  - Kakao 주소 검색 API
 
## 🧠 핵심 설계

### 1. 상품 옵션 구조

상품은 다음 3가지 타입을 지원합니다.

- SINGLE: 단일 옵션
- MULTI_PACK: 동일 상품 다중 구성
- SET: 서로 다른 상품 조합

---

### 2. 장바구니 구조

장바구니는 다음과 같이 분리 설계했습니다.

- cartItem: 상품 단위
- cartItemDetail: 옵션 단위 (색상, 사이즈 등)

➡️ 하나의 상품에 여러 옵션을 유연하게 담기 위한 구조

---

### 3. 주문 구조

- orders
- order_item

➡️ 주문 시점의 가격/옵션을 스냅샷으로 저장


## 🗂️ ERD

(이미지 첨부)

## 🗄️ DB 초기화

1. DB 생성

```sql
CREATE DATABASE daewon_shop;
CREATE TABLE user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    phone VARCHAR(50),
    address VARCHAR(255),
    role VARCHAR(20) NOT NULL,
    profile_image_url varchar(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    parent_id BIGINT,
    CONSTRAINT fk_category_parent
        FOREIGN KEY (parent_id)
        REFERENCES category(category_id)
        ON DELETE SET NULL
);

CREATE TABLE product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seller_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
	sale_type VARCHAR(20) NOT NULL,
    pack_quantity INT DEFAULT 1,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    price INT NOT NULL,
    gender VARCHAR(20),
    fit VARCHAR(50),

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_product_seller
        FOREIGN KEY (seller_id)
        REFERENCES user(user_id),

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
        REFERENCES category(category_id)
);

CREATE TABLE product_detail (
    product_detail_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,

    color VARCHAR(50),
    size VARCHAR(50),

    stock INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_product_detail_product
        FOREIGN KEY (product_id)
        REFERENCES product(product_id)
        ON DELETE CASCADE
);

CREATE TABLE product_image (
    image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,

    image_url VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL, -- MAIN / DESCRIPTION
    sort_order INT DEFAULT 0,

    CONSTRAINT fk_product_image_product
        FOREIGN KEY (product_id)
        REFERENCES product(product_id)
        ON DELETE CASCADE
);

CREATE TABLE cart_item (
    cart_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    user_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    sale_type varchar(50) NOT NULL,

    CONSTRAINT fk_cart_item_user
        FOREIGN KEY (user_id)
        REFERENCES user(user_id)
        ON DELETE CASCADE
);


CREATE TABLE order_detail (
    order_detail_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    order_id BIGINT NOT NULL,
    product_detail_id BIGINT NOT NULL,

    price INT NOT NULL,
    quantity INT NOT NULL,

    delivery_status VARCHAR(20) NOT NULL, -- READY / SHIPPING / DONE

    CONSTRAINT fk_order_detail_order
        FOREIGN KEY (order_id)
        REFERENCES orders(order_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_order_detail_product_detail
        FOREIGN KEY (product_detail_id)
        REFERENCES product_detail(product_detail_id)
);

CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    user_id BIGINT NOT NULL,

    total_price INT NOT NULL,
    status VARCHAR(20) NOT NULL,

    recipient_name VARCHAR(100),
    phone VARCHAR(50),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    zipcode VARCHAR(20),

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id)
        REFERENCES user(user_id)
);

CREATE TABLE address (
    address_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    user_id BIGINT NOT NULL,

    recipient_name VARCHAR(100) NOT NULL, -- 받는 사람
    phone VARCHAR(50) NOT NULL,

    address_line1 VARCHAR(255) NOT NULL, -- 기본 주소
    address_line2 VARCHAR(255),          -- 상세 주소
    zipcode VARCHAR(20),

    is_default BOOLEAN DEFAULT FALSE,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_address_user
        FOREIGN KEY (user_id)
        REFERENCES user(user_id)
        ON DELETE CASCADE
);

CREATE TABLE product_set (
    product_set_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,  -- 세트 상품

    child_product_id BIGINT NOT NULL, -- 구성 상품
    quantity INT NOT NULL,

    CONSTRAINT fk_set_product
        FOREIGN KEY (product_id)
        REFERENCES product(product_id),

    CONSTRAINT fk_set_child
        FOREIGN KEY (child_product_id)
        REFERENCES product(product_id)
);


CREATE TABLE cart_item_detail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    cart_item_id BIGINT NOT NULL,
    product_detail_id BIGINT NOT NULL,

    CONSTRAINT fk_cart_item_detail_cart_item
        FOREIGN KEY (cart_item_id)
        REFERENCES cart_item(cart_item_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_cart_item_detail_product_detail
        FOREIGN KEY (product_detail_id)
        REFERENCES product_detail(product_detail_id)
        ON DELETE CASCADE
        
	
);

```

## 🚀 실행 방법

1. 프로젝트 클론

```bash
git clone https://github.com/your-repo.git
./gradlew bootRun


---
```

## ⚠️ 트러블슈팅

### 1. 옵션 구조 설계 문제
- 문제: 상품 옵션이 다양해지면서 테이블 구조가 복잡해짐
- 해결: cartItem / cartItemDetail로 분리

### 2. JSON 파싱 오류
- 문제: API 응답이 HTML로 반환됨
- 원인: 인증 실패 / 잘못된 URL

## 📈 개선 예정

- 결제 시스템 연동
- 상품 리뷰 기능
- 관리자 페이지 고도화
