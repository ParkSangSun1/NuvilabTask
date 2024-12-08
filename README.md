## 앱 설치 및 실행 방법
local.properies 설정
```
API_KEY = "WRr7xLRrlLtdf9QQWwD%2FLiMHqf5Ng%2B7KaREML3arpzi%2FfrYCpwwPWLqxSK7mjDdWgHckNCILn%2FEBRYbMPlE47g%3D%3D"
BASE_URL = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"
```
실기기로 빌드

## 사용 기술 스택
- Kotlin & Compose
- Retrofit & Okhttp
- Coroutine & Flow
- Hilt
- Room
- Firebase crashlytics

## 아키텍처
- UI -> ViewModel -> Repository -> DataSource(Local & Remote)

## 세부 요구사항
- API 연동 : 공공 기관의 초단기예보조회 api를 사용하여 단기 날씨 정보(기온, 습도)를 가져와 사용자에게 보여주는 앱을 구현하였습니다.
- 에러 처리 : Api response에 대한 성공과 실패 유무, 그리고 error라면 code, message, type을 구분하여 처리하였습니다.
- 재시도 로직 : Interceptor에서 response가 실패라면 지수 백오프 알고리즘을 이용해 3번 min, max 사이 랜덤한 값으로 delay를 준 후 재시도하고 그래도 실패일 경우 마지막 한번 시도한 response를 전달하도록 구현하였습니다.
- 데이터 영속성 및 동기화 : Room db를 이용해 Weather 정보를 저장하고 가장 최신 정보를 flow를 이용해 ui에서 보여줬습니다.
- 오프라인 모드 지원 : Room에 저장된 데이터를 오프라인 시 표시합니다(하단에 네트워크 오류 문구 표시), 네트워크가 다시 연결되었을 시 실패한 api call을 다시 시도하게 구현하였습니다.
- 백그라운드 데이터 동기화 : WorkManager를 사용해 15분 마다 api call을 실행해 데이터를 최신 상태로 유지할 수 있게 구현하였습니다.
- 코드 보안 : 빌드 타입이 release 일때 proguard 적용하였습니다.
- 로그 관리 및 크래시 리포팅 : Firebase Crashlytics를 사용하여 크래시에 대해 대응할 수 있게 구현하였습니다.
![image](https://github.com/user-attachments/assets/d0832e89-a8a0-43e0-95fc-0c44fdf7cad7)
