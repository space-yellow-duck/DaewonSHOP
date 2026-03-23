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

## 🚀 실행 방법

1. 프로젝트 클론

```bash
git clone https://github.com/your-repo.git
./gradlew bootRun


---

# 7. 트러블슈팅 (이거 있으면 수준 올라감)

```md
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
