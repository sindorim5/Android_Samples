# Lifecycle Test
---
## 구조
### MainActivity
### ComposeActivity
- MainScreen
- SecondScreen
### SecondActivity
---
## 동작
```kotlin
DisposableEffect(key1 = lifecycleOwner.lifecycle) {
    val observer = LifecycleEventObserver { source, event ->
        Log.d(TAG, "MainScreen observe: $event")
    }
    lifecycleOwner.lifecycle.addObserver(observer)

    onDispose {
        Log.d(TAG, "MainScreen: onDispose")
        lifecycleOwner.lifecycle.removeObserver(observer)
    }
}
```
**MainActivity** | onCreate -> onStart -> onResume <br>
*(To Compose Activity Button Click)* <br>
**MainActivity** | -> onPause <br>
**ComposeActivity** | onCreate -> onStart -> onResume <br>
**MainScreen** | onCreate -> onStart -> onResume <br>
**MainActivity** | onStop <br>
*(To Second Screen Button Click)* <br>
**MainScreen** | onPause -> onStop <br>
**SecondScreen** | onCreate -> onStart <br>
**MainScreen** | onDispose <br>
**SecondScreen** | onResume <br>
*(To Second Activity Button Click)* <br>
**SecondScreen** | onPause <br>
**ComposeActivity** | onPause <br>
**SecondActivity** | onCreate -> onStart -> onResume <br>
**SecondScreen** | onStop <br>
**ComposeActivity** | onStop <br>
*(To Main Activity Button Click)* <br>
**SecondActivity** | onPause <br>
**MainActivity** | onCreate -> onStart -> onResume <br>
**SecondActivity** | onStop
