# parkmate-review-read-service

주차장 리뷰 조회 전용 마이크로서비스입니다. MongoDB를 사용하여 리뷰 데이터를 저장하고, Kafka 이벤트를 통해 실시간으로 데이터를 동기화합니다.

## 📋 목차

- [개요](#개요)
- [기술 스택](#기술-스택)
- [아키텍처](#아키텍처)
- [주요 기능](#주요-기능)
- [API 문서](#api-문서)
- [설치 및 실행](#설치-및-실행)
- [개발 환경 설정](#개발-환경-설정)
- [배포](#배포)
- [프로젝트 구조](#프로젝트-구조)

## 🎯 개요

Review Read Service는 주차장 리뷰 시스템의 조회 전용 서비스입니다. 다음과 같은 특징을 가지고 있습니다:

- **CQRS 패턴**: 리뷰 조회 전용 서비스로 읽기 성능 최적화
- **실시간 동기화**: Kafka 이벤트를 통한 실시간 데이터 동기화
- **하이브리드 데이터 저장**: MongoDB (실시간) + RDB (배치 집계)
- **마이크로서비스 아키텍처**: Eureka Client, OpenFeign을 통한 서비스 간 통신

## 🛠 기술 스택

### Backend
- **Java 17**
- **Spring Boot 3.4.3**
- **Spring Cloud 2024.0.1**
- **Spring Data MongoDB**
- **Spring Kafka**
- **Spring Cloud OpenFeign**
- **Spring Cloud Netflix Eureka Client**

### Database
- **MongoDB**: 리뷰 데이터 저장 (실시간)
- **MySQL**: 배치 집계 데이터 (Feign Client로 조회)

### Message Queue
- **Apache Kafka**: 이벤트 기반 데이터 동기화

### Documentation
- **Swagger/OpenAPI 3.0**: API 문서화

### DevOps
- **Docker**: 컨테이너화
- **Gradle**: 빌드 도구
- **GitHub Actions**: CI/CD

## 🏗 아키텍처

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client App    │    │  Review Service │    │   User Service  │
└─────────┬───────┘    └─────────┬───────┘    └─────────┬───────┘
          │                      │                      │
          │                      │                      │
          ▼                      ▼                      ▼
┌─────────────────────────────────────────────────────────────────┐
│                    API Gateway / Load Balancer                  │
└─────────────────────────┬───────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Review Read Service                          │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │   Controller│  │   Service   │  │  Repository │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │   Kafka     │  │   Feign     │  │   MongoDB   │            │
│  │  Consumer   │  │   Client    │  │             │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
└─────────────────────────────────────────────────────────────────┘
                          │
          ┌───────────────┼───────────────┐
          ▼               ▼               ▼
┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│   Kafka     │  │   Eureka    │  │   MongoDB   │
│             │  │   Client    │  │             │
└─────────────┘  └─────────────┘  └─────────────┘
```

## 🚀 주요 기능

### 1. 리뷰 목록 조회
- 주차장별 리뷰 목록 조회
- 커서 기반 페이지네이션
- 최신순 정렬
- 사용자 이름 포함 조회

### 2. 사용자 리액션 조회
- 특정 리뷰에 대한 사용자 리액션 타입 조회
- LIKE, DISLIKE, NONE 상태 확인

### 3. 리뷰 요약 정보 조회
- 주차장별 평균 평점
- 총 리뷰 개수
- 실시간 데이터 + 배치 집계 데이터 합산

### 4. 실시간 데이터 동기화
- 리뷰 생성/수정/삭제 이벤트 처리
- 사용자 프로필 업데이트 이벤트 처리
- 리뷰 리액션 업데이트 이벤트 처리

## 📚 API 문서

### Swagger UI
- URL: `http://localhost:8086/swagger-ui.html`
- API 문서 및 테스트 가능

### 주요 API 엔드포인트

#### 1. 리뷰 목록 조회
```http
GET /api/v1/reviews?parkingLotUuid={uuid}&cursor={timestamp}&size={number}
```

#### 2. 사용자 리액션 조회
```http
GET /api/v1/reviews/{reviewUuid}/reaction
Header: X-User-UUID: {userUuid}
```

#### 3. 리뷰 요약 조회
```http
GET /api/v1/reviews/summary?parkingLotUuid={uuid}
```

## 💻 설치 및 실행

### 사전 요구사항
- Java 17
- Docker & Docker Compose
- MongoDB
- Apache Kafka
- Eureka Server

### 1. 프로젝트 클론
```bash
git clone <repository-url>
cd reviewreadservice
```

### 2. 환경 변수 설정
`.env` 파일을 생성하고 다음 환경변수를 설정하세요:

```env
AWS_ACCESS_KEY_ID=your_aws_access_key
AWS_SECRET_ACCESS_KEY=your_aws_secret_key
EUREKA_HOST=your_eureka_host
KAFKA_BOOTSTRAP_SERVERS=your_kafka_servers
MONGODB_URI=your_mongodb_uri
```

### 3. 애플리케이션 설정
`src/main/resources/application.yml` 파일을 생성하고 설정하세요:

```yaml
spring:
  application:
    name: review-read-service
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS}
  data:
    mongodb:
      uri: ${MONGODB_URI}
  cloud:
    openfeign:
      client:
        config:
          default:
            connectTimeout: 5000
            readTimeout: 5000

eureka:
  client:
    service-url:
      defaultZone: http://${EUREKA_HOST}/eureka/

server:
  port: 8086

logging:
  level:
    com.parkmate.reviewreadservice: DEBUG
```

### 4. 로컬 실행
```bash
# Gradle 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

### 5. Docker 실행
```bash
# Docker 이미지 빌드
docker build -t review-read-service .

# Docker Compose 실행
docker-compose -f docker-compose-review-read.yml up -d
```

## 🔧 개발 환경 설정

### IDE 설정
- **IntelliJ IDEA** 또는 **Eclipse** 권장
- **Lombok** 플러그인 설치
- **Spring Boot** 플러그인 설치

### 코드 스타일
- **Java 17** 문법 사용
- **Lombok** 어노테이션 활용
- **Builder 패턴** 사용
- **Domain-Driven Design** 적용

### 테스트
```bash
# 단위 테스트 실행
./gradlew test

# 통합 테스트 실행
./gradlew integrationTest
```

## 🚀 배포

### GitHub Actions CI/CD
프로젝트는 GitHub Actions를 통해 자동 배포됩니다:

1. **dev** 브랜치에 푸시 시 자동 배포
2. **AWS ECR**에 Docker 이미지 푸시
3. **EC2** 서버에 자동 배포

### 수동 배포
```bash
# 1. 빌드
./gradlew build -x test

# 2. Docker 이미지 생성
docker build -t review-read-service .

# 3. ECR 푸시
docker tag review-read-service:latest {ecr-repository}/review-read-service:latest
docker push {ecr-repository}/review-read-service:latest

# 4. 서버 배포
docker-compose -f docker-compose-review-read.yml down
docker-compose -f docker-compose-review-read.yml up -d
```

## 📁 프로젝트 구조

```
src/main/java/com/parkmate/reviewreadservice/
├── common/                          # 공통 모듈
│   ├── config/                      # 설정 클래스
│   │   ├── MongoConfig.java        # MongoDB 설정
│   │   └── SwaggerConfig.java      # Swagger 설정
│   ├── converter/                   # 데이터 변환기
│   ├── exception/                   # 예외 처리
│   └── response/                    # 공통 응답 모델
├── kafka/                          # Kafka 관련
│   ├── config/                     # Kafka 설정
│   ├── consumer/                   # 이벤트 컨슈머
│   └── event/                      # 이벤트 모델
└── reviewread/                     # 리뷰 조회 도메인
    ├── application/                # 애플리케이션 서비스
    ├── domain/                     # 도메인 모델
    ├── dto/                        # 데이터 전송 객체
    ├── infrastructure/             # 인프라스트럭처
    └── presentation/               # 프레젠테이션 계층
```

### 주요 클래스 설명

#### Domain Models
- **ReviewRead**: MongoDB에 저장되는 리뷰 도메인 모델
- **ReviewReactionRead**: 리뷰 리액션 도메인 모델
- **ReviewStatus**: 리뷰 상태 열거형

#### Services
- **ReviewReadService**: 리뷰 조회 비즈니스 로직
- **ReviewReadIntegrationService**: Kafka 이벤트 통합 처리

#### Controllers
- **ReviewReadController**: REST API 엔드포인트

#### Kafka Consumers
- **ReviewReadConsumer**: 리뷰 생성 이벤트 처리
- **ReviewUpdatedEventConsumer**: 리뷰 수정 이벤트 처리
- **ReviewDeletedEventConsumer**: 리뷰 삭제 이벤트 처리
- **ReviewReactionUpdatedConsumer**: 리액션 업데이트 이벤트 처리
- **UserProfileUpdatedConsumer**: 사용자 프로필 업데이트 이벤트 처리

## 🔄 Kafka 이벤트 처리

### 구독하는 토픽
- `review.review.created`: 리뷰 생성
- `review.review.updated`: 리뷰 수정
- `review.review.deleted`: 리뷰 삭제
- `review.review-reaction.updated`: 리뷰 리액션 업데이트
- `user.user-profile.updated`: 사용자 프로필 업데이트
- `user.review-join-user.created`: 리뷰-사용자 조인 데이터 생성

### 이벤트 처리 흐름
1. **리뷰 생성**: `ReviewCreatedEvent` + `CreateReviewJoinUserEvent` 조합 처리
2. **리뷰 수정**: MongoDB 문서 업데이트
3. **리뷰 삭제**: 논리적 삭제 (status = DELETED)
4. **리액션 업데이트**: 리뷰 카운트 및 리액션 문서 업데이트
5. **사용자 프로필 업데이트**: 리뷰 내 사용자 이름 업데이트

## 📊 모니터링 및 로깅

### 로그 레벨
- **DEBUG**: 개발 환경에서 상세 로그
- **INFO**: 일반적인 애플리케이션 로그
- **WARN**: 경고 상황
- **ERROR**: 오류 상황

### 주요 로그 포인트
- Kafka 이벤트 수신
- MongoDB 쿼리 실행
- Feign Client 호출
- API 요청/응답

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 📞 문의

프로젝트에 대한 문의사항이 있으시면 이슈를 생성해 주세요.

---

**ParkMate Team** © 2025 
