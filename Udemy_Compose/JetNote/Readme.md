# JetNote

## Managing State and Data in Compose

- Hoisting the State further out of the Composable Tree
- We can use ViewModel -*Single Source of Truth*-

## DI - Dependency Injection

- Dependency : an object that another object requires to function

```
fun main() {
// 	val car = Car()
	val engine = Engine()
    val turbo = TurboEngine()
    val car = Car(engine, turbo)
    
    car.engine.start()
    car.turbo.start()
    car.startCar()
}
class Engine() {
    fun start() {
        println("Engine X started...")
    }
}
class TurboEngine() {
    fun start() {
        println("Turbo Engine X started...")
    }
}
class Car(val engine: Engine, val turbo: TurboEngine) {
//  Violates Single Responsibility of Class
//     val engine = Engine()
    fun startCar() {
        println("Starting Car...")
    }
}
```

## Hilt

- AppModule : Add bindings to hilt. We don't have to make builders every time.
- @HiltAndroidApp, @AndroidEntryPoint, @HiltViewModel
- @InstallIn(SingletonComponent::class), @Module, @Singleton, @Provides
- @Inject