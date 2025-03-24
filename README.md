Coffee 프로젝트

📌 프로젝트 소개
- Coffee는 팀 내에서 커피를 사는 순서를 관리하는 API 기반 서비스입니다.
- backend mybatis 에서 jpa로 마이그레이션 했습니다.

🚀 주요 기능

1️⃣ 멤버 조회 API
- 현재 등록된 모든 멤버 리스트를 반환합니다.

- API Endpoint: api/v1/account/getAllAccount

- Response JSON:
```
[
    {
        "id": 1,
        "name": "이주안",
        "phone": "111",
        "buy": true
    },
    {
        "id": 2,
        "name": "박명수",
        "phone": "222",
        "buy": true
    },
    {
        "id": 3,
        "name": "김철수",
        "phone": "333",
        "buy": true
    },
    {
        "id": 4,
        "name": "유재석",
        "phone": "444",
        "buy": false
    }
]
```

2️⃣ 커피 구매 순서 조회 API
- 다음 커피를 살 멤버를 확인할 수 있습니다.
- 모든 멤버가 한 번씩 샀다면 순서를 초기화합니다.

- API Endpoint: /api/v1/account/getBuyerByCurrentDate

- Response JSON:
```
{
    "buyerInform": "오늘의 커피 계산은 이주안 님 입니다.",
    "dateInform": "오늘의 날짜는 THURSDAY 입니다.",
    "message": "오늘은 커피를 먹는날!"
}
```
(모든 멤버가 한 번씩 샀다면 "reset": true 반환)

🛠 기술 스택
- Backend: Java17, Spring Boot 3.X
- Database: Maria DB
- Containerization: Docker

📌 추가 기능 예정
- 멤버 추가 / 삭제 API
- 커피 구매 기록 저장
- 관리자 기능 추가
