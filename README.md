# DiaryProject
JavaSE 개인 프로젝트

<img src="https://postfiles.pstatic.net/MjAxOTA1MDhfMTM1/MDAxNTU3MjQ1Nzc2NTk4.kkKbqHkZ_UaWcv37-b5olOIpOfSIolPVxySRPBqg0Ywg.rlcHPzdgXD18EEoQwYtc39Kx4ik-uImyUK2AljQHKMIg.PNG.kwjing93/MyMoney_Diary1.png?type=w966">

<img src="https://blogfiles.pstatic.net/MjAxOTA1MDhfMTY1/MDAxNTU3MzIyNzM5NjM5.iaSnZ08mRGqUuXQbZ-AmYvrQkTUka9ldIYGAb_L4j88g.2reX5HIZeYTlnyq9lSeHvciNLe2M3nlCSDkVNW7G7N4g.PNG.kwjing93/MyMoney_Diary2-1.png">

<< 1 메모등록 기능 >>

[ DB ] 
- ConnectionManager 클래스를 정의하여 db연결, 접속객체 획득, 반납 처리

[ 공통 디자인 ]

- java awt swing 컴포넌트의 사용
- layout manager 이용한 ui 컴포넌트 배치

[ 이벤트 처리 ]
- eventListener 및 adapter를 이용한 이벤트 처리
- 코드의 간결성을 위한 내부 익명 클래스 기반의 이벤트 처리

[ 공통 컴포넌트 ]
- String 처리 기능을 모아놓은 StringUtil 클래스를 정의하여 확장자, 시간, 날짜 편집

<img src="https://postfiles.pstatic.net/MjAxOTA1MDhfMjI4/MDAxNTU3MzIyNzUxNTMx.exg0ZPO1IMaoFOmLWv1ZH9PeXDTcPYHuFznuutO-3fkg.I0kKp-4hWpxK2Xay8WGgerbQsth8EPVsPKom_nzkwl8g.PNG.kwjing93/MyMoney_Diary2-2.png?type=w966">

[ 일당 계산 처리 ]
- 사용자가 입력한 시간 기준 일당 계산 로직을 처리하는 메서드 정의

<img src="https://postfiles.pstatic.net/MjAxOTA1MDhfMTM5/MDAxNTU3MjQ1NzgxMjMy.FfC_ICBjIX6EtBwGJUPkZmo0v0cEIL13U16H9e3rcYQg.g5r8WUH2TLRIxbfdiNRwU05hJfHKh0eRHd99vM4L_0og.PNG.kwjing93/MyMoney_Diary3.png?type=w966">

<< 2 검색 기능 >>

[ 검색 기능 처리 ]
- like문을 이용한 검색 처리
- 오라클의 문자관련 함수 적용

<img width="70%" src="https://postfiles.pstatic.net/MjAxOTA1MDhfMTI3/MDAxNTU3MjQ1NzgzNTYx.-ojDBt5KiBreaetW-z0DASkOd-gqmYUhFDiEcQssXdcg.xax0Kl3r7lVErTesOtuFAh3kspw0TDTu4YMmMsiqOoMg.PNG.kwjing93/MyMoney_Diary4.png?type=w966">

<< 3 폴더생성 기능 >>

[ 처리 ]
- JTree 클래스를 사용하여 folder 구조 표현
- InputDialog와 MessageDialog를 활용한 유저 알림

