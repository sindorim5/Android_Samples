package com.sindorim.composetest.screens.stt

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class SpeechViewModel : ViewModel() {
    val transcription = mutableStateOf("")
    val isRecording = mutableStateOf(false)

}