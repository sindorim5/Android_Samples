# JetAReader

## rememberSaveable
- remember 함수는 Composition 내에서 Composable이 유지될 때만 올바르게 동작한다.
- 화면이 회전이 되는 등, re-compose 될 때는 값이 유실된다.
- rememberSaveable은 화면회전과 같은 환경구성 변경 및 프로세스의 종료에서도 상태를 보존시킨다.