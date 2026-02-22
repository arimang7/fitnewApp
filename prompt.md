당신은 Android 시니어 개발자입니다. thinking_level: HIGH로 복잡한 앱 아키텍처를 설계하세요. Android Studio에서 바로 빌드 가능한 완전한 운동 기록 앱을 Kotlin + Jetpack Compose + Room DB + Hilt로 생성하세요.

앱 기능:

- 홈: 운동 목록 표시 (날짜, 운동명, 세트/회/무게, 총 시간)
- 운동 추가/편집: 운동명, 세트 수, 회 수, 무게, 메모 입력. 카메라로 사진 첨부 (MediaStore)
- 기록 보기: 캘린더 뷰로 날짜 선택, 상세 차트 (Chart 라이브러리 사용, MPAndroidChart)
- 통계: 주/월 총 운동량 그래프, PB 기록 추적
- 설정: 목표 설정, 알림, 데이터 백업 (SharedPreferences)

필수 요구사항:

- MVVM 아키텍처, Repository 패턴
- Room DB: WorkoutEntity (id, date, exercise, sets, reps, weight, duration, photoUri)
- Compose UI: Material3 디자인, 테마 다크/라이트 지원
- 권한: 카메라, 저장소
- 완전한 코드: build.gradle, MainActivity, 모든 ViewModel/Repository/Screen, DB 스키마
- 에러 핸들링, 로딩 상태, 빈 상태 UI 포함
- 한국어 지원 (strings.xml)

출력 형식:

1. 프로젝트 구조 트리
2. build.gradle (app, project)
3. 전체 Kotlin 코드 파일별로 (MainActivity.kt, WorkoutViewModel.kt 등)
4. strings.xml, themes.xml
5. 실행 지침

최적화: 성능 좋고, Material You 디자인, 접근성 지원. 테스트 가능한 코드로 작성하세요.
