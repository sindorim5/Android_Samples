# JetWeatherForecast

## Navigation

## LaunchedEffect
- Composable에서 Composition이 일어날 때 suspend fun을 실행시켜주는 Composition
- Key를 두어서 Key가 변경될 때만 LaunchedEffect의 suspend fun을 취소하고 재실행함

## produceState
- 내부에선 LaunchedEffect를 사용하고 있다.
- Compose Enter시 Launch 되고 Leave 상태가 되면 Cancel 된다.