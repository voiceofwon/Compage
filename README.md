# [Compage](http://www.ajoucomp.com, "꼼 홈페이지")

## :bear: 아주대학교 전자공학과 소학회 "Comp D&A" 홈페이지입니다. :bear:

* * *

## :bear: 소개

### 제작 기간
> 2022.12.31 ~ 2023.2.28

### 최초 배포
> 2023.2.29

### 운영기간
> 2023.2.29 ~ 운영중

### :bear: 구현 기능

> * 관리자(동아리 임원진), 사용자(동아리 회원)CRUD + 회원가입 및 로그인 로그아웃
> * 학번, 학년 별 회원 관리
> * 게시판 및 기능별 접근권한 관리
> * 공지사항, 활동내역, 학습 자료 게시판 (+ 파일 업로드, 다운로드)
> * 소학회 소개 내 연혁 관리

### :bear: 버전 업데이트

* 1.0.8 활동내역 사진 업로드 버그 수정, directory bug fix
    * 배포한 컴퓨터의 절대경로에 접근하지 못하는 에러 수정.
* 1.0.11 글 조회시 줄바꿈 되지않는 오류 수정 
  * 줄바꿈도 인식하도록 textarea태그로 변경
* 1.0.12 글 조회 overflow bug fix
* 1.1.0 관리자 페이지 회원 조회 학번별 Sorting 기능 추가 및 bug fix
  * Posting 시 빈 제목, 빈 작성자 등에서 에러 페이지 수정
* 1.1.2 Post edit page에서 기존 글을 불러오지 못 하는 bug fix
  * 글 수정시 기존 글 불러오기
* 1.1.4 상단고정공지(TopFixedPost) 기능 추가 및 글쓰기 view 개선
* 1.1.8 상단고정공지 파일첨부 추가 글 조회 버그 Fix


### :bear: 개발 환경
> * Intellij
> * Postman
> * GitHub
> * MySQL cmd
> * SourceTree
> * Visual Studio Code

### :bear: 사용 기술

#### 백엔드

> * JAVA openjdk
> * Tomcat 9
> * SpringBoot 3.0.2
>   * Spring Security
>   * Spring data JPA

#### Build Tool
> * Gradle

#### Database
> * MySQL

#### Infra
> * 동아리 방 내부의 서버컴퓨터에 WAR 파일을 직접 배포하였습니다.
> * Ubuntu 20.04.5

#### 프론트엔드
> * HTML/CSS
> * Thymeleaf

#### 기타 라이브러리
> * Lombok

### :bear: ERD
![image](https://user-images.githubusercontent.com/104613104/230004185-69cc3c78-2735-4039-94d5-2ba6513e404c.png)


### :bear: 사이트 제작 목적

#### :cat: 동아리 내부적 사유

 23학년도 동아리 임원진으로 활동
  * ~2018년도 까지 php로 운영되던 동아리 사이트가 있었으나 기존 DB와 파일들이 모두 소실됨.
  * 이후 기존 서버 관리자 분의 졸업과 COVID-19로 인한 비대면 수업으로 인해 임시 사이트로 Goole-site를 통해
    제작한 사이트가 운영 중이나 효과적이지 않아 새로운 도아리 홈페이지 제작을 마음먹게 됨.
  * 비대면 수업을 거치며 선후배간의 교류가 온라인으로 이루어 지는 경향이 있어 신입생 및 재학생들을 위한
    위한 자료제공 및 동아리 활동내역 게시판 구현
  * 기존 동아리 임원진의 동아리 인원관리가 수기 및 엑셀 파일로 일일이 대조하는 방식으로 불편함이 있어
    사이트 내에서 동아리 회원(+ 졸업생 명단)을 관리하기 위한 기능 구현
 
#### :cat: 개인적 사유
  
  서버 개발에 대해 학습하며 실제 수요에 맞는 기능을 구현하고, 개발의 전 과정을 겪는 경험의 필요성을 느끼고 있었습니다.
  
  최초에는 웹 크롤링과 Jwt를 활용한 학교내 학과별, 단과대학별, 테마별 공지사항을 태그화 하여
  
  사용자에게 제공하는 프로젝트를 구상하고 있었습니다. 하지만 백엔드 API 개발은 비교적 쉽게 체험할 수 있으나,
  
  웹 개발자로서 웹 애플리케이션의 전 과정에 대해 숙지하는 경험을 하는 것이 더 값진 경험이라고 생각하여 본 사이트 제작을 결정하였습니다.
  
  특히, 실사용자가 정해진 사이트를 직접 운영해보는 경험은 쉽게 할 수 없을 것이라고 생각하였습니다. 
  
  
  추후에 제가 동아리 활동을 하지않더라도 운영되어야 할 사이트이므로, 공부하며 흘려넘긴 것들에 대해
  
  생각할 수 있을 것이며, 운영 중에 발생하는 에러에 대해 어떻게 대응해야 할 지 스스로 알게 되는 것을 넘어
  
  이후의 서버관리자에게 인수인계되어야 하므로 책임감 높은 프로젝트경험을 할 것이라 판단하였습니다.
  
  
  개발 연습을 위한 개발, 프로젝트로 남는 것이 아닌 실제로 다년간 사이트를 운영, 유지보수 할 것이며
  
  사용자의 피드백을 통해 개발에서 어떤 부분이 고려돼야 할 지 몸소 깨닫게 되는 경험을 기대하고 있습니다.
  
  (++ 실제 개발과정의 경험, 운영에서의 경험 추가 예정)




