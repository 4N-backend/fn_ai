# 📦 물류 관리 및 배송 플랫폼 📦 
> MSA 기반 국내 물류 및 배송 시스템 개발
![image](https://github.com/user-attachments/assets/cd4eced0-758d-4be1-a498-7b51d75863e0)


---

## 📌 프로젝트 소개

**스파르타 물류**는 국내 B2B 기업 간 상품의 주문, 배송, 재고 관리를 위한 **MSA 기반 물류 플랫폼**입니다.  
실제 택배 및 유통 시스템과 유사한 구조를 반영하여, **허브 간 배송**, **업체 간 거래**, **AI 기반 발송 예측**, **Slack 연동 알림**, **배송 경로 추적** 등 다양한 기능을 구현합니다.

> 실무 환경에서 요구되는 MSA 설계, 서비스 간 통신, 도메인 설계(DDD), 권한 제어, 트랜잭션 처리, 캐싱 전략, Docker 기반 환경 구축을 학습하며 개발한 프로젝트입니다.

---

## 🧑🏻‍💻 개발 인원 및 역할

| 구성원        | 역할                                                                                                |
| ----------------- |---------------------------------------------------------------------------------------------------------|
| **BE(L): 남정길** | Order, Product, Auth 도메인 담당<br />  |
| **BE: 최진영**    | Delivery 도메인 담당<br />  |
| **BE: 노석준**    | Hub, Company도메인 담당<br />  |
| **BE: 공희진**    | User,  Notification 도메인 담당<br />  |                                    

<br/><br/>

## 🧩 주요 기능 요약

| 도메인 | 기능 요약 |
|--------|-----------|
| **허브 관리** | 전국 17개 허브 CRUD, 캐싱, 논리 삭제 |
| **업체 관리** | 생산업체/수령업체 등록, 허브 연동 |
| **상품 관리** | 업체별 상품 등록, 허브 상품관리 연계 |
| **주문 관리** | 주문 생성/취소, 재고 반영, 배송 자동 생성 |
| **배송 관리** | 배송 경로 설정, 상태 추적, 담당자 순번 배정 |
| **배송 담당자 관리** | 허브/업체 담당자 구분, 순차 배정 로직 구현 |
| **사용자 인증/인가** | JWT + Spring Security, 권한 기반 제어 |
| **Slack 알림** | 주문 발생 시 배송 담당자 Slack 알림 전송 |
| **AI 연동** | Gemini API를 통한 발송 마감 시간 예측 |
| **논리 삭제 관리** | 모든 엔티티에 `deleted_at`, `deleted_by` 적용 |
| **서치 기능** | 검색/정렬/페이징 기능 적용 |

<br/><br/>


## 🏛 아키텍처
![architecture](https://github.com/user-attachments/assets/b09b94e3-e27f-4c28-a547-3a619aeeed19)

- **Spring Cloud Gateway**: API 진입 지점
- **Spring Cloud Eureka**: 서비스 디스커버리
- **각 마이크로서비스**: 허브, 업체, 주문, 배송 등
- **Slack API 연동**: 배송 Slack 알림 처리
- **Gemini API 연동**: 배송 예측 생성형 AI 활용
- **Zipkin**: 분산 추적


<br/><br/>

## ⚙️ 기술 스택

| 항목 | 사용 기술 |
|------|-----------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x, Spring Cloud |
| **Auth** | Spring Security, JWT |
| **Database** | PostgreSQL (서비스별 스키마 분리) |
| **Docs** | Swagger (OpenAPI) |
| **Service Comm** | REST API + FeignClient |
| **DevOps** | Docker, Docker Compose |
| **Monitoring** | Zipkin |
| **ETC** | Slack API, Gemini API |

<br/><br/>

## 📈 ERD
<img width="632" alt="스크린샷 2025-03-25 오후 2 01 13" src="https://github.com/user-attachments/assets/c5882c79-9566-48b2-88ad-5d19332b0f01" />


