# 💸💰💴핀테크 과제💵💷🪙

## 😘프로젝트 기능 및 설계

### 👥회원 가입 기능
  - 회원 가입에는 이메일 주소, 휴대폰 번호, 이름, 생년월일 입력한다.
  - 일반적으로 모든 사용자는 회원 가입 시 USER 권한 (일반 권한)을 지닌다.
  - 이메일 인증을 통해 이메일을 확인한다

### 🪧로그인 기능
- 사용자는 로그인 시 회원가입 때 사용한 이메일과 패스워드가 일치해야 한다
- 로그인 시 이메일 유효성 검사를 통해 입력 조건에 맞게 정보가 입력되었는지 확인한다.
- 로그인 실패 했을 때 따로 에러 처리를 하여 처리한다.
    - 아이디를 입력하지 않았을 때
    - 비밀번호를 입력하지 않았을 때
    - 아이디 또는 비밀번호를 잘못 입력했을 때
- JWT Token을 이용하여 로그인 유무를 판단한다
  
### 🔍계좌 검색 기능
- 로그인한 사용자만 계좌 검색이 가능하다.
- 은행명, 계좌 별칭(예: 월급 통장)으로 검색 가능하다

### 📊계좌 관리 기능
- 로그인한 사용자는 계좌 관리를 할 수 있다.
  - 계좌 생성
  - 사용자가 생성하고 보유 중인 계좌만 삭제 가능
  - 사용자가 생성하고 보유 중인 계좌에서만 금액 인출 가능
    - 금액 인출 시 pin 비밀번호(pin-password)를 입력해야 한다.  
  - 금액 입금

### 💸송금 기능
  - 송금 시 pin 비밀번호(pin-password)를 입력해야 한다.
  - 로그인한 사용자만 보유한 계좌에서 송금할 수 있다.
  - 잔액 부족 시 계좌 송금 불가
  - 1일 최대 이체 금액을 제한하여 한도 초과 시 계좌 송금 불가
### 📝송금 이력 조회 기능
  - 로그인한 사용자만 자신이 송금한 이력을 볼 수 있다.
    - 송금자 계좌, 수신자 계좌, 송금 금액, 송금 시간, 거래 ID를 조회할 수 있다.
    - 사용자가 원하는 거래 내역을 빠르게 찾을 수 있도록 여러 필터를 제공
      - 특정 기간 (지난 1주일, 1개월 특정 날짜 등)
      - 금액 범위 (특정 금액 이상/이하의 송금 내역 조회)
      - 정렬 ( 날짜, 금액 등 정렬 가능하고 기본으로 최신 순 정렬)
  - 거래 내역이 많을 수 있음으로 페이징 처리

## ERD
![ERD Diagram](https://github.com/mingang211/ZB-fintech/blob/main/src/ZB-fintech.drawio.png)

## Trouble Shooting


## Tech Stack

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
