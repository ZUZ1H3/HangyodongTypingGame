# 한교동 단어 타이핑 게임 🐟
2023년 2학년 2학기 객체지향언어2 기말 프로젝트입니다.

## 작품 개요 
이 게임은 ‘한교동’이라는 캐릭터를 기반으로 제작했다. 단어를 타이핑하여 배고픈 한교동에게 음식을 주는 컨셉이다. 
게임은 총 3개의 레벨로 구성되어 있으며, 레벨이 높아질수록 단어 생성 속도와 떨어지는 속도가 증가한다. 3레벨은 게임오버가 될 때까지 지속된다.

## 프로젝트 구조
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/29fa7bdf-85c8-4aaa-97fe-8ebff71720db" alt="HangyodongTypingGame1"  width="700px">

## 게임 설명
### 레벨 1: 한교동의 식사 시간

* 1.8초마다 단어가 생성된다.
* 단어와 함께 햄버거, 피자 등의 음식 아이콘이 내려온다.
* 게이지가 다 차면 레벨 2로 넘어간다.

### 레벨 2: 한교동의 간식 시간

* 1.5초마다 단어가 생성된다.
* 단어와 함께 케이크, 아이스크림 등의 간식 아이콘이 내려온다.
* 게이지가 다 차면 레벨 3으로 넘어간다.

### 레벨 3: 아직도 배고픈 한교동
* 1.2초마다 단어가 생성된다.
* 하트가 모두 깎일 때 까지 게임이 진행된다.

#### [ 하트(생명) ] 
총 5개의 하트가 있으며, 단어를 바닥에 떨어뜨리면 하트가 하나씩 깎인다. 모든 하트가 깎이면 게임이 종료된다. 

#### [ 게이지 ] 
게임 패널 상단에 위치하며, 한교동의 배부름 게이지를 보여준다. 게이지가 다 차면 다음 레벨로 넘어가며 리셋된다. 

#### [ 빨간 문어 ] 
단어 옆에 음식아이콘이 같이 떨어지는데, 간혹 빨간 문어 아이콘이 나오는 경우가 있다. 문어가 있는 단어를 타이핑하면 스코어가 10점 감소한다.

#### [ 콤보 ] 
단어를 오타나 실수 없이 5번 이상 제거하면 Combo가 발생한다. 오른쪽 패널에 콤보 label이 뜬다. Combo 발생 시 단어 하나 당 20점씩 증가한다. 

#### [ 스코어 ] 
단어 하나 당 10점씩 증가하며, 콤보 발생 시 20점씩 증가한다. 빨간 문어를 타이핑하면 10점 감소한다.

## 실행 과정
#### (1) 메인 화면 
게임 시작, 게임 방법, 단어 저장, 랭킹 확인을 할 수 있다. 이름을 입력하고 시작 버튼을 누르면 게임이 
시작된다. 이름을 입력하지 않고 시작 버튼을 누르면 “이름을 입력 해주세요!” 라는 문구가 뜬다. 
버튼에 마우스를 올리면 버튼 색상이 바뀐다. 

<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/09c7a927-a37a-454a-ac9b-b9db0bf15f77" alt="HangyodongTypingGame2" width="400px">
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/81eb000d-50dd-44ef-82c0-fdec0a34543e" alt="HangyodongTypingGame3" width="400px">

#### (2) 게임 방법 화면 
 ‘메인 화면’ 버튼을 누르면 (1)로 되돌아간다. 
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/67c8fe89-4afe-41cf-9708-5bfec8322a35" alt="HangyodongTypingGame4" width="700px">

#### (3) 단어 저장 화면 
단어를 입력하고 엔터치면 저장할 수 있으며, txt파일에 이미 있는 단어일 경우 저장되지 않는다. 
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/dde9b262-c06c-4f95-a8ab-d6d0071a2ff2" alt="HangyodongTypingGame5" width="400px">
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/ba616603-c3e8-48bb-b7dc-88c33da1022f" alt="HangyodongTypingGame6" width="400px">

#### (4) 랭킹 화면 
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/1f5fce4e-1567-4e8e-80e2-a39fc3563786" alt="HangyodongTypingGame7" width="700px">

#### (5) 게임 실행 화면 
게임 시작을 했을 때 다이얼로그가 약 3초동안 떴다가 자동으로 사라진 후, 단어가 떨어진다.
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/d4296b8f-f608-48eb-96d8-9c5b8f2480d4" alt="HangyodongTypingGame8" width="400px">
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/d2ada975-dd2d-4e2d-b0dc-c17d191e02a4" alt="HangyodongTypingGame9" width="400px">

#### 단어를 떨어뜨리거나 오타가 나면 슬픈 표정을, 잘 입력하면 접시에 음식이 생기고 웃는 표정을 짓는다.

<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/d2e8961a-f5ad-4391-9991-3f4e20746e48" alt="HangyodongTypingGame10"  width="300px">
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/c219aabc-e8d8-4acd-a4b3-4d60d4be2fdb" alt="HangyodongTypingGame11"  width="300px">

#### 2레벨, 3레벨이 되었을 때의 다이얼로그 
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/e3648728-dea7-43d5-a5dc-74145c94b7d7" alt="HangyodongTypingGame12"  width="300px">
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/8552acd0-deec-4810-8a20-e15537a311b9" alt="HangyodongTypingGame13"  width="300px">


#### 게임오버가 되면 이름과 점수에 저장되고, 종료 또는 메인으로 갈 수 있다. 
<img src="https://github.com/ZUZ1H3/HangyodongTypingGame/assets/147241368/bae468c6-dc36-49a5-a73b-94589c6acd46" alt="HangyodongTypingGame14"  width="700px">
