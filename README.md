# DiaryProject
JavaSE 개인 프로젝트

<img src="https://postfiles.pstatic.net/MjAxOTA1MDhfMTM1/MDAxNTU3MjQ1Nzc2NTk4.kkKbqHkZ_UaWcv37-b5olOIpOfSIolPVxySRPBqg0Ywg.rlcHPzdgXD18EEoQwYtc39Kx4ik-uImyUK2AljQHKMIg.PNG.kwjing93/MyMoney_Diary1.png?type=w966">

1 메모등록 기능
<img src="https://postfiles.pstatic.net/MjAxOTA1MDhfMSAg/MDAxNTU3MjQ1Nzc5NTUw.AoVMDOQNG0P-G0nhaFp4SqDrwa3tyzBVw1L2YNWeM1kg.WWh0pABHP-O_hiP72HYl2201bgns8AENJwUGtlIoubAg.PNG.kwjing93/MyMoney_Diary2.png?type=w966">

[ DB ] 
- ConnectionManager 클래스를 정의하여 db연결, 접속객체 획득, 반납 처리

[ 디자인 ]
- 클래스 상속
- java swing 컴포넌트의 사용
- layout manager 사용
- eventListener, adapter 사용
- JTree 클래스를 사용하여 folder 구조 생성

[ 처리 ]
- String 처리 기능을 모아놓은 StringUtil 클래스를 정의하여 확장자, 시간, 날짜 편집
- 형변환, String method로 값을 편집


2 검색 기능
<img src="https://postfiles.pstatic.net/MjAxOTA1MDhfMTM5/MDAxNTU3MjQ1NzgxMjMy.FfC_ICBjIX6EtBwGJUPkZmo0v0cEIL13U16H9e3rcYQg.g5r8WUH2TLRIxbfdiNRwU05hJfHKh0eRHd99vM4L_0og.PNG.kwjing93/MyMoney_Diary3.png?type=w966">

[ 처리 ]
- sql문 like '%' || ? || '%' 을 활용한 검색 
- 사용자가 입력한 시간을 기준으로 일당을 계산해주는 메서드 정의


3 폴더생성 기능
<img width="70%" src="https://postfiles.pstatic.net/MjAxOTA1MDhfMTI3/MDAxNTU3MjQ1NzgzNTYx.-ojDBt5KiBreaetW-z0DASkOd-gqmYUhFDiEcQssXdcg.xax0Kl3r7lVErTesOtuFAh3kspw0TDTu4YMmMsiqOoMg.PNG.kwjing93/MyMoney_Diary4.png?type=w966">


