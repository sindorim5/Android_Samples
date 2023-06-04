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
**MainActivity** | onCreate -> onStart -> onResume
*(To Compose Activity Button Click)*
**MainActivity** | -> onPause
**ComposeActivity** | onCreate -> onStart -> onResume
**MainScreen** | onCreate -> onStart -> onResume
**MainActivity** | onStop 
*(To Second Screen Button Click)*
**MainScreen** | onPause -> onStop
**SecondScreen** | onCreate -> onStart
**MainScreen** | onDispose
**SecondScreen** | onResume
*(To Second Activity Button Click)*
**SecondScreen** | onPause
**ComposeActivity** | onPause
**SecondActivity** | onCreate -> onStart -> onResume
**SecondScreen** | onStop
**ComposeActivity** | onStop
*(To Main Activity Button Click)*
**SecondActivity** | onPause
**MainActivity** | onCreate -> onStart -> onResume 
**SecondActivity** | onStop
