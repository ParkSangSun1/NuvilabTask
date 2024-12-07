## 앱 설치 및 실행 방법
- 다운로드 후 실행

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
- 데이터 영속성 및 동기화 : 
