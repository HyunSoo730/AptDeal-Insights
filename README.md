# HappyHouse 🏠

<div align="center">
<img width="400" alt="집구하기 프로젝트 이미지" src="https://i.postimg.cc/KctgsPn0/image.webp">
</div>

# HappyHouse 🏠 - 서울시 부동산 매물 정보 제공 서비스
> **서울시의 부동산 매물 정보를 제공하고 다양한 서비스를 제공하여 사용자들이 효과적으로 매물을 검색하고 비교할 수 있도록 도와주는 서비스입니다.**<br>


## 👍 프로젝트 소개

HappyHouse는 서울시의 부동산 매물 정보를 한 곳에서 확인하고 비교할 수 있는 플랫폼입니다. 아파트 매매 실거래가, 전월세 실거래가, 주변 학군 정보, 주변 교통 정보, 통계 차트 등 다양한 정보를 제공하여 사용자들이 효율적으로 매물을 검색하고 비교할 수 있도록 지원합니다. 또한 사용자들 간의 정보 교환을 위한 실시간 커뮤니티와 AI 챗봇 서비스를 제공하여 보다 편리한 매물 탐색 경험을 제공합니다.

부동산 매물 검색은 복잡하고 시간 소모적인 작업이 될 수 있습니다. 여러 웹사이트를 방문하여 정보를 수집하고 비교하는 것은 쉽지 않습니다. HappyHouse는 이러한 불편함을 해소하고자 서울시의 부동산 매물 정보를 통합하여 제공하는 것을 목표로 합니다. 사용자는 원하는 조건을 입력하여 매물을 검색하고, 관심 있는 매물을 비교하며, 커뮤니티를 통해 다른 사용자들과 정보를 공유할 수 있습니다.

HappyHouse는 사용자 친화적인 인터페이스와 직관적인 기능을 통해 부동산 매물 검색 과정을 간소화하고 최적화합니다. 또한 AI 챗봇을 활용하여 사용자의 질문에 즉각적으로 응답하고 맞춤형 추천을 제공합니다. 이를 통해 사용자는 보다 편리하고 효율적으로 매물을 탐색하고 의사 결정을 내릴 수 있습니다.

프로젝트를 통해 부동산 매물 검색에 소요되는 시간과 노력을 줄이고, 사용자들에게 신뢰할 수 있는 정보를 제공하여 만족도를 높이는 것이 저희 프로젝트 HappyHouse의 궁극적인 목표입니다. 추가하고 싶은 기능 및 성능 개선을 계속해서 해 나갈 것입니다.

## 🏆 프로젝트를 통해 얻은 점

 🌟 **Querydsl 활용 역량 강화**
 - 기존에는 `JPA`의 인터페이스에 Querydsl을 함께 구현하여 사용했지만(`Impl` 형태로 Querydsl 사용), 이번 프로젝트에서는 `@Repository` 어노테이션만으로 특정 DB에 접근하도록 Querydsl을 독립적으로 활용하는 방법을 배웠습니다. 이를 통해 좀 더 간결하고 효율적인 쿼리 작성이 가능해졌습니다.
 - Querydsl의 `JPAQueryFactory`를 사용하여 동적 쿼리 생성, 복잡한 조건 처리 등을 수행할 수 있게 되었습니다. 특히 `BooleanBuilder`를 활용하여 null-safe한 동적 쿼리 처리가 가능해졌고, `where()`에 다양한 조건을 추가하여 쿼리를 쉽게 구성할 수 있게 되었습니다.

 🚀 **Vue.js를 활용한 프론트엔드 개발 경험**
 - 이번 프로젝트에서 처음으로 프론트엔드를 맡아 Vue.js로 개발을 진행하면서, Vue.js의 장점과 컴포넌트 기반 개발 방식에 대해 알게 되었습니다.
 - Vue.js의 반응형 시스템, 단방향 데이터 바인딩, 컴포넌트 재사용성 등을 활용하여 효율적이고 유지보수성 높은 코드를 작성할 수 있었습니다.

 🏗️ **프로젝트 전반적인 아키텍처 설계 및 개발 리딩 경험**
 - 프로젝트의 백엔드 개발을 리드하면서 프로젝트 전반적인 아키텍처 설계를 주도적으로 진행하였습니다. 모듈화, 계층화 아키텍처를 적용하여 확장성과 유지보수성을 높이는 데 주력하였습니다.
 - RESTful API 설계, 데이터베이스 모델링, 비즈니스 로직 구현 등 백엔드 개발의 핵심 부분을 담당하면서 개발 팀을 이끄는 경험을 쌓을 수 있었습니다.

 🔄 **데이터 동기화 및 배치 처리 자동화**
 - Spring Batch를 활용하여 대량의 매물 데이터를 주기적으로 수집하고 분석하는 배치 작업을 자동화하였습니다.
 - 배치 작업의 스케줄링, 장애 대응, 모니터링 등을 경험하면서 안정적인 데이터 동기화와 배치 처리 역량을 기를 수 있었습니다.

 🤝 **협업 및 커뮤니케이션 능력 향상**
 - 프로젝트를 진행하면서 팀원들과의 효과적인 커뮤니케이션, 업무 분담, 일정 관리 등 협업 능력을 한층 더 향상시킬 수 있었습니다.
 - 정기적인 회의, 코드 리뷰, 문서화 등을 통해 프로젝트의 진행 상황을 공유하고 함께 문제를 해결해나가는 경험을 쌓았습니다.

이번 프로젝트를 통해 Querydsl 활용, Vue.js 프론트엔드 개발, 프로젝트 아키텍처 설계 및 개발 리딩, 데이터 동기화 및 배치 처리 자동화, 협업 및 커뮤니케이션 능력 등 다양한 측면에서 역량을 강화할 수 있었습니다. 앞으로도 이러한 경험을 바탕으로 더욱 성장하는 개발자가 되기 위해 노력하겠습니다.
 

## 🛠️ 기술 스택
### Environment
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Vue.js](https://img.shields.io/badge/Vue.js-4FC08D?style=for-the-badge&logo=vuedotjs&logoColor=white)

### Config
![npm](https://img.shields.io/badge/npm-CB3837?style=for-the-badge&logo=npm&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

### Development
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-3399FF?style=for-the-badge&logo=QueryDSL&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-000000?style=for-the-badge&logo=mybatis&logoColor=white)
![Spring Batch](https://img.shields.io/badge/Spring%20Batch-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Chart.js](https://img.shields.io/badge/Chart.js-FF6384?style=for-the-badge&logo=chartdotjs&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white)

### Database
![AWS RDS](https://img.shields.io/badge/AWS%20RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white)

### DevOps
![AWS EC2](https://img.shields.io/badge/AWS%20EC2-232F3E?style=for-the-badge&logo=amazonec2&logoColor=white)

### Communication
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Mattermost](https://img.shields.io/badge/Mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white)

## 👥 팀 소개
### 팀 구성
|    조현수      |          고한강         |
| :------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------: |
|   <img width="160px" src="https://i.postimg.cc/FzHqR8gZ/image.png" />    |                      <img width="160px" src="https://user-images.githubusercontent.com/50205887/207570536-f5a82e48-99a1-4399-91d3-75fc5f8f3349.png" />    |
|   [@hyunsoo](https://github.com/HyunSoo730)   |    [@hangang](https://github.com/hangang)  |
| 백엔드 개발 리드 & 프론트엔드 개발 | 프론트엔드 개발 리드 & 백엔드 개발 |

## 🙋‍♂️ 팀원별 역할 및 기여도
| 이름 | 역할 및 기여도 |
|------|--------------|
| 💪 조현수 | - 🚀 **백엔드 개발 리드**로서 프로젝트 아키텍처 설계 및 개발 총괄<br>- 🎨 **Spring Boot**를 활용한 **RESTful API 구현 및 문서화**<br>- ⚙️ **Spring Batch**를 활용한 **데이터 수집 및 분석 배치 작업 구현**<br>- 💾 **데이터베이스 성능 최적화** 및 **인덱싱 작업 수행**<br>- 🌐 **배포 및 운영 환경 구축 및 관리**<br>- 🎨 **Vue.js**를 활용한 **프론트엔드 개발** 및 **UI/UX 디자인 참여**<br>- 🤝 **팀 내 커뮤니케이션 및 협업 주도**하며 **프로젝트 일정 관리**|
| 💪 고한강 | - 🎨 **Vue.js**를 활용한 **프론트엔드 개발 리드** 및 **UI/UX 디자인 총괄**<br>- 📊 **Chart.js**를 활용한 **데이터 시각화 및 통계 차트 구현**<br>- 🌿 **Tailwind CSS**를 활용한 **반응형 디자인 및 스타일링**<br>- 🤖 **AI 챗봇 서비스 연동** 및 **사용자 상호작용 개발**<br>- 🧪 **프론트엔드 테스트 및 성능 최적화 작업 수행**<br>- 🚀 **Spring Boot**를 활용한 **백엔드 개발 참여** 및 **RESTful API 구현**<br>- 📝 **프로젝트 문서화** 및 **사용자 가이드 작성 담당**|

## 📊 ERD (Entity-Relationship Diagram)
![ERD](https://i.postimg.cc/sghZhbVD/ERD.png)

프로젝트의 데이터베이스는 위와 같은 ERD를 기반으로 설계되었습니다.

- `apt_rent_sale` 테이블: 아파트 전월세 및 매매 정보를 저장하는 테이블입니다. 아파트 이름, 주소, 거래 유형(전세/월세/매매), 가격, 면적 등의 정보를 관리합니다.
- `agent_review` 테이블: 공인중개사에 대한 사용자 리뷰 정보를 저장하는 테이블입니다. 리뷰 내용, 평점, 작성자, 작성일 등의 정보를 포함합니다.
- `area` 테이블: 지역 정보를 저장하는 테이블입니다. 지역 이름, 구, 동 등의 정보를 관리합니다.
- `apt_review` 테이블: 아파트에 대한 사용자 리뷰 정보를 저장하는 테이블입니다. 리뷰 내용, 평점, 작성자, 작성일 등의 정보를 포함합니다.
- `like_area` 테이블: 사용자가 관심 있는 지역 정보를 저장하는 테이블입니다. 사용자 ID와 지역 ID를 매핑하여 관리합니다.
- `subway_station` 테이블: 지하철 역 정보를 저장하는 테이블입니다. 역 이름, 호선, 위치 등의 정보를 관리합니다.
- `dongcode` 테이블: 행정동 코드 정보를 저장하는 테이블입니다. 동 이름, 코드 등의 정보를 포함합니다.
- `chat_rooms` 테이블: 채팅방 정보를 저장하는 테이블입니다. 채팅방 이름, 생성자, 생성일 등의 정보를 관리합니다.
- `apt_deal_info` 테이블: 아파트 거래 정보를 저장하는 테이블입니다. 아파트 ID, 거래 일자, 거래 금액 등의 상세 정보를 포함합니다.
- `apt_hsd_info` 테이블: 아파트 세대 정보를 저장하는 테이블입니다. 세대 수, 난방 방식, 건축 연도 등의 정보를 관리합니다.

ERD에서 볼 수 있듯이, 아파트 매물 정보를 중심으로 사용자 리뷰, 관심 지역, 지하철 역, 행정동 등 다양한 관련 정보가 테이블로 구성되어 있습니다. 이를 통해 아파트 매물 검색, 사용자 리뷰 관리, 관심 지역 설정, 주변 정보 제공 등의 기능을 효과적으로 지원할 수 있는 구조로 되어 있습니다.

또한 `apt_deal_info`와 `apt_hsd_info` 테이블을 통해 아파트 거래 정보와 세대 정보를 별도로 관리함으로써 상세한 매물 정보를 제공할 수 있습니다.


## ☄️ 주요 기능

### 매물 정보 검색
- 지역구별, 아파트별로 매물 정보를 검색할 수 있습니다.
- 매매 실거래가, 전월세 실거래가 정보를 확인할 수 있습니다.
- 관심 있는 매물을 찜하고 비교할 수 있습니다.

### 학군 및 교통 정보 제공
- 선택한 아파트 주변의 초등학교, 중학교, 고등학교 학군 정보를 제공합니다.
- 하버사인 알고리즘을 활용하여 해당 아파트로부터 가장 가까운 지하철역 4곳을 조회하고, 각 역까지 걸리는 시간을 제공합니다.

### 통계 차트 제공
- 지역구별, 아파트별 매물 가격 추이를 차트로 시각화하여 한눈에 파악할 수 있습니다.
- 매물의 평수별 가격 비교 차트를 제공하여 예산에 맞는 적절한 평수를 선택할 수 있도록 도와줍니다.
- 해당 지역의 평균 매매가, 전세가 등을 차트로 제공하여 시세 파악에 용이합니다.

### 커뮤니티 및 AI 챗봇
- 사용자들 간의 정보 교환을 위한 실시간 커뮤니티를 제공합니다.
- 해당 아파트나 지역에 대한 생생한 후기와 정보를 공유할 수 있습니다.
- AI 챗봇을 통해 사용자의 질문에 즉각적으로 답변하고, 맞춤형 매물 추천을 제공합니다.

### 실시간 채팅 기능
- 관심 매물에 대해 공인중개사와 실시간 채팅을 할 수 있습니다.
- 매물 관련 문의사항이나 추가 정보 요청 등을 채팅을 통해 빠르게 소통할 수 있습니다.

### 매물 등록 및 관리
- 공인중개사는 간편하게 매물을 등록하고 관리할 수 있습니다.
- 등록된 매물의 정보를 수정하고, 거래 완료된 매물을 쉽게 관리할 수 있습니다.

## 📺 화면 구성

| 메인 페이지 | 매물 검색 화면 |
| :-------------------------------------------: | :-------------------------------------------: |
| <img width="400" alt="메인 페이지" src="https://i.postimg.cc/C50zbKxF/image.png"/> | <img width="400" alt="매물 검색 화면" src="https://i.postimg.cc/CLFR1d27/image.png"/> |
| 메인 페이지에서는 서비스의 주요 기능과 최신 매물 정보를 한눈에 확인할 수 있습니다. 매물 검색, 커뮤니티, 관심 지역 설정 등의 기능을 바로 이용할 수 있는 UI를 제공합니다. | 매물 검색 화면에서는 다양한 조건을 설정하여 원하는 매물을 검색할 수 있습니다. 지역, 매매/전월세, 가격, 면적 등의 조건을 입력하고 검색 결과를 확인할 수 있습니다. |

| 매물 상세 정보 | 매물 등록 |
| :-------------------------------------------: | :-------------------------------------------: |
| <img width="400" alt="매물 상세 정보" src="https://i.postimg.cc/SxPRDdx1/image.png"/> | <img width="400" alt="매물 등록" src="https://i.postimg.cc/YSm9VF4r/image.png"/> |
| 매물 상세 정보 화면에서는 선택한 매물의 상세한 정보를 확인할 수 있습니다. 매물의 사진, 가격, 면적, 위치, 옵션 등의 정보를 제공하며, 해당 매물에 대한 사용자 리뷰와 질문/답변을 볼 수 있습니다. | 매물 등록 화면에서는 공인중개사가 새로운 매물을 등록할 수 있습니다. 매물의 기본 정보, 사진, 상세 설명 등을 입력하고 매물을 게시할 수 있습니다. |

| 커뮤니티 및 AI 챗봇 | 채팅방 화면 |
| :-------------------------------------------: | :-------------------------------------------: |
| <img width="400" alt="커뮤니티 및 AI 챗봇" src="https://i.postimg.cc/qRs7BdXW/AI.png"/> | <img width="400" alt="채팅방 화면" src="https://i.postimg.cc/NFQj5PdF/image.png"/> |
| 커뮤니티 및 AI 챗봇 화면에서는 사용자들 간의 정보 공유와 소통이 이루어집니다. 매물 관련 질문과 답변, 지역 정보 등을 게시판 형태로 확인할 수 있습니다. 또한 AI 챗봇을 통해 부동산 관련 질문에 대한 자동 응답을 받을 수 있습니다. | 채팅방 화면에서는 사용자들 간의 실시간 채팅이 가능합니다. 매물 문의, 거래 조건 협의 등 개인 간 커뮤니케이션을 위한 채팅 기능을 제공합니다. |

| 마이 페이지 |
| :-------------------------------------------: |
| <img width="400" alt="마이 페이지" src="https://i.postimg.cc/zG2b450j/image.png"/> |
| 마이 페이지에서는 사용자의 개인 정보 관리, 관심 매물 목록, 문의 내역 등을 확인할 수 있습니다. 사용자는 프로필 정보를 수정하고, 관심 매물을 추가/삭제할 수 있으며, 자신이 작성한 문의 내역을 관리할 수 있습니다. |

## 🏛️ 아키텍처
### 디렉토리 구조

```plaintext
root
├── backend
│   ├── Dockerfile
│   ├── dao.iml
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── likelion
│       │   │           └── dao
│       │   │               ├── DaoApplication.java
│       │   │               ├── config
│       │   │               │   ├── BatchConfig.java
│       │   │               │   ├── OpenApiConfig.java
│       │   │               │   └── WebMvcConfig.java
│       │   │               ├── controller
│       │   │               │   ├── JobController.java
│       │   │               │   ├── RegionController.java
│       │   │               │   └── UserController.java
│       │   │               ├── dto
│       │   │               │   ├── JobDto.java
│       │   │               │   ├── RegionDto.java
│       │   │               │   └── UserDto.java
│       │   │               ├── entity
│       │   │               │   ├── Job.java
│       │   │               │   ├── Region.java
│       │   │               │   └── User.java
│       │   │               ├── repository
│       │   │               │   ├── JobRepository.java
│       │   │               │   ├── RegionRepository.java
│       │   │               │   └── UserRepository.java
│       │   │               └── service
│       │   │                   ├── JobService.java
│       │   │                   ├── KakaoAlarmService.java
│       │   │                   ├── RegionService.java
│       │   │                   └── UserService.java
│       │   └── resources
│       │       ├── application.properties
│       │       └── application-prod.properties
│       └── test
│           └── java
│               └── com
│                   └── likelion
│                       └── dao
│                           └── DaoApplicationTests.java
├── frontend
│   ├── README.md
│   ├── node_modules
│   ├── package-lock.json
│   ├── package.json
│   ├── public
│   │   ├── favicon.ico
│   │   ├── index.html
│   │   ├── logo192.png
│   │   ├── logo512.png
│   │   ├── manifest.json
│   │   └── robots.txt
│   └── src
│       ├── App.css
│       ├── App.js
│       ├── App.test.js
│       ├── components
│       │   ├── Footer.js
│       │   ├── Header.js
│       │   ├── JobDetail.js
│       │   ├── JobList.js
│       │   ├── KakaoAlarmModal.js
│       │   ├── MyPage.js
│       │   └── RegionSelect.js
│       ├── index.css
│       ├── index.js
│       ├── logo.svg
│       ├── pages
│       │   ├── AlarmPage.js
│       │   ├── JobDetailPage.js
│       │   ├── JobListPage.js
│       │   ├── MainPage.js
│       │   └── MyPage.js
│       ├── reportWebVitals.js
│       ├── services
│       │   ├── JobService.js
│       │   ├── KakaoAlarmService.js
│       │   ├── RegionService.js
│       │   └── UserService.js
│       └── setupTests.js
└── README.md
```

## 🎉 프로젝트 결과 및 성과
- 🏘️ 서울시의 방대한 부동산 매물 정보를 한 곳에서 제공하여 사용자들의 매물 검색 경험을 개선하였습니다.
- 📊 통계 차트와 비교 기능을 통해 사용자들이 효과적으로 매물을 분석하고 의사 결정을 내릴 수 있도록 지원하였습니다.
- 🎓 주변 학군 및 교통 정보를 제공하여 사용자들이 매물 선택 시 고려해야 할 요소를 파악할 수 있게 하였습니다.
- 💬 실시간 커뮤니티를 통해 사용자들 간의 정보 공유와 소통을 활성화하였습니다.
- 🤖 AI 챗봇을 도입하여 사용자들의 질문에 즉각적으로 응답하고 맞춤형 추천을 제공하여 사용자 만족도를 높였습니다.
- ⏰ Spring Batch를 활용해 대량의 매물 데이터를 수집하고 분석하는 배치 작업을 자동화하여(배치 프로세싱 자동화 구축) 데이터 처리 효율성을 향상시켰습니다.
- 🗄️ 데이터베이스 인덱싱과 쿼리 최적화를 통해 대용량 데이터 처리 시 성능을 개선하였습니다.
- 🌐 AWS EC2를 활용하여 클라우드 환경에서 서비스를 배포하고 운영함으로써 확장성과 가용성을 확보하였습니다.

## 🔧 개선 사항 및 추후 계획
- 🙌 사용자들의 피드백을 적극 수렴하여 서비스 사용성과 편의성을 지속적으로 개선해 나갈 예정입니다.
 - 예) 매물 필터링 옵션 다양화, 사용자 맞춤형 추천 알고리즘 고도화(컨텐츠 기반 추천 알고리즘 생각 중) 등
- 🔍 더 많은 지역으로 서비스 확대를 고려하고 있습니다. 서울시 외 수도권 및 주요 도시로 매물 정보 제공 범위를 넓힐 계획입니다.
- 📈 서비스 모니터링 및 로깅 체계를 강화하여 사용자 행동 분석과 서비스 개선에 활용할 예정입니다.
- 🚀 증가하는 트래픽과 데이터 볼륨에 대응하기 위해 시스템 아키텍처 최적화와 확장성 향상에 주력할 계획입니다.

## 🚨 트러블슈팅
### 대용량 데이터 처리 시 성능 저하
- 증상: 매물 데이터 수집 및 분석 배치 작업 수행 시 대용량 데이터 처리로 인해 성능 저하 문제가 발생하였습니다.
- 원인: 데이터 볼륨 증가에 따른 배치 작업 처리 시간 증가와 데이터베이스 쿼리 성능 저하가 주된 원인이었습니다.
- 해결: 데이터베이스 인덱싱과 쿼리 최적화를 통해 데이터 조회 속도를 개선하였습니다. 또한 배치 작업의 청크 사이즈를 조정하여 처리 속도를 향상시켰습니다.

### 복잡한 쿼리로 인한 성능 저하
- 증상: 매물 검색 시 복잡한 조건을 포함한 쿼리로 인해 응답 속도가 저하되는 문제가 발생하였습니다. 특정 검색 조건에서 쿼리 실행 시간이 1초 이상 소요되는 경우가 관측되었습니다.
- 원인: 복잡한 조인과 필터 조건, 인덱스 미사용 등으로 인해 쿼리 실행 계획이 비효율적으로 수립되었습니다. 
- 해결: 쿼리 분석을 통해 병목 지점을 식별하고, 인덱스 생성 및 쿼리 조건 최적화를 진행하였습니다. 불필요한 조인을 제거하고, 필터 조건을 단순화하여 쿼리 실행 속도를 개선하였습니다. 그 결과 평균 쿼리 실행 시간이 140ms에서 52ms로 약 63% 감소하였습니다.

### JPA N+1 문제로 인한 성능 저하
- 증상: 매물 상세 정보 조회 시 연관된 엔티티를 로딩하는 과정에서 N+1 문제가 발생하여 쿼리 실행 횟수가 과도하게 증가하였습니다.
- 원인: JPA의 지연 로딩(Lazy Loading) 전략을 사용하면서 연관된 엔티티를 개별적으로 로딩하는 과정에서 N+1 문제가 발생하였습니다.
- 해결: 페치 조인(Fetch Join)을 활용하여 연관된 엔티티를 한 번에 로딩하도록 최적화하였습니다. JPQL 쿼리에 페치 조인을 명시하여 필요한 엔티티를 즉시 로딩하도록 조정하였습니다. 이를 통해 쿼리 실행 횟수를 대폭 감소시키고 성능을 개선할 수 있었습니다.


## 🖥️ 프로젝트 시연 영상
### 아래 사진 클릭

<a href="https://drive.google.com/file/d/1d6x19sChlf0rLAQullodcuSrQbx7-d36/view?usp=sharing" target="_blank">
    <img src="https://i.postimg.cc/C50zbKxF/image.png" alt="프로젝트 시연 영상" width="400"/>
</a>




프로젝트를 진행하면서 다양한 기술적 챌린지를 경험했지만, 팀원들과의 협업과 노력 및 끈기를 바탕으로 성공적으로 프로젝트를 완수할 수 있었습니다. 이번 프로젝트를 통해 협업의 중요성과 문제 접근 방식의 새로운 접근법을 배웠고, 이를 통해 제가 가진 개발 역량을 한층 더 발전시킬 수 있었습니다. 앞으로도 이러한 경험을 바탕으로 더욱 성장해나가겠습니다.
