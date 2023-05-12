package com.sindorim.kotlinflowexample

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue

        emit(startingValue)

        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Int>(3)
    val sharedFlow = _sharedFlow.asSharedFlow()

    val mutableState = mutableStateOf("this")

    fun squareNumber(number: Int) {
        viewModelScope.launch {
            _sharedFlow.emit(number * number)
        }
    }

    fun incrementCounter() {
        _stateFlow.value += 1
    }

    fun thisAndThat() {
        if (mutableState.value == "this") mutableState.value = "that"
        else mutableState.value = "this"
    }

    init {
//        collectFlows()
        for (i in 1 until 8) {
            squareNumber(i)
        }

        println("sdr FLOW")
        viewModelScope.launch {
            sharedFlow.collect { value ->
                delay(2000L)
                println("sdr FLOW 2: shared flow num is $value")
            }
        }
        viewModelScope.launch {
            sharedFlow.collect { value ->
                delay(3000L)
                println("sdr FLOW 3: shared flow num is $value")
            }
        }
//        squareNumber(3) // collector를 설치하고 함수를 실행시켜야 값을 출력함
    }

    // Do not do like this in UI Layer
    private fun collectFlow() {
        /*
        countDownFlow.onEach {  time ->
            println("The onEach time is $time")
        }.launchIn(viewModelScope) // same with bottom code
        */

        viewModelScope.launch {
            val count = countDownFlow
                .filter { time ->
                    time % 2 == 0
                }
                .map { time ->
                    time * time
                }
                .onEach { time ->
                    println("sdr The onEach time is $time")
                }
                .count {
                    it % 2 == 0
                }
            println("sdr The count is $count")

            val reduceResult = countDownFlow.reduce { accumulator, value ->
                accumulator + value
            }
            println("sdr The reduce is $reduceResult")

            val foldResult = countDownFlow.fold(100) { accumulator, value ->
                accumulator + value
            }
            println("sdr The fold is $foldResult")

//                .collect { time ->
////                    delay(1500L)
//                    println("The current time is $time")
//                }
            // Every Single Emission

            /*
            countDownFlow.collectLatest { time ->
                delay(1500L)
                println("The current time is $time")
            } // Cancel the old Emission
            */
        }
    }

    private fun collectFlows() {
//        val flow1 = flow {
//            emit(1)
//            delay(500L)
//            emit(2)
//        }
        val flow1 = (1..5).asFlow()
        viewModelScope.launch {
            flow1.flatMapConcat { value ->
                flow {
                    emit(value + 1)
                    delay(500L)
                    emit(value + 2)
                }
            }
                .conflate()
                .collect { value ->
                    println("sdr The value is $value")
                }
        }
    }


}