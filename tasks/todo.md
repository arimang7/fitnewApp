# Fitness App To-Do List

## 계획 및 설계

- [x] 요구사항 분석 (`prompt.md`, `CLAUDE.md` 검토)
- [x] 구현 계획 (`implementation_plan.md` 작성)

## 안드로이드 프로젝트 셋업

- [ ] 기본 프로젝트 뼈대 생성 (`settings.gradle.kts`, `build.gradle.kts` 등)
- [ ] 의존성 추가 (Compose, Room, Hilt, MPAndroidChart 등)
- [ ] `AndroidManifest.xml`, `strings.xml` 설정

## 데이터 계층 구현

- [ ] `WorkoutEntity.kt` (Room Entity) 구현
- [ ] `WorkoutDao.kt` 구현
- [ ] `AppDatabase.kt` 구현
- [ ] Hilt 모듈 (`DatabaseModule.kt`) 추가

## 리포지토리 계층 구현

- [ ] `WorkoutRepository.kt` 구현 및 Hilt 연결

## UI 계층 구현

- [ ] UI 테마 설정 (`Theme.kt`, `Color.kt`, `Type.kt`)
- [ ] `WorkoutViewModel.kt` 구현
- [ ] 내비게이션 구조 설계 (`MainActivity.kt`)
- [ ] `HomeScreen.kt` 구현
- [ ] `AddEditScreen.kt` 구현 (카메라 연동 포함)
- [ ] `HistoryScreen.kt` 구현 (캘린더 및 MPAndroidChart 연동)
- [ ] `StatsScreen.kt` 구현 (주/월별 통계 차트)
- [ ] `SettingsScreen.kt` 구현 (SharedPreferences 프레퍼런스)

## 검증 및 마무리

- [ ] 안드로이드 빌드 검증 (`./gradlew assembleDebug`)
- [ ] 에러 핸들링 및 빈 상태 UI 확인
- [ ] 결과 검토 및 문서화 (`tasks/lessons.md` 업데이트 - 필요한 경우)
