package com.sindorim.kotlinflowexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
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

    init {
        collectFlows()
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
                .filter {  time ->
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