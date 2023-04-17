# JetWeatherForecast

## Navigation
- `implementation "androidx.navigation:navigation-compose:$nav_version"`
- `navController.navigate(route = WeatherScreens.MainScreen.name, )`

## LaunchedEffect
- Composable에서 Composition이 일어날 때 suspend fun을 실행시켜주는 Composition
- Key를 두어서 Key가 변경될 때만 `LaunchedEffect`의 suspend fun을 취소하고 재실행함

## produceState
- 내부에선 `LaunchedEffect`를 사용하고 있다.
- Compose Enter시 Launch 되고 Leave 상태가 되면 Cancel 된다.

## Coil
- `rememberAsyncImagePainter` is a lower-level API that may not behave as expected in all cases.
- `AsyncImage` uses `AsyncImagePainter` to load `model`