package com.sindorim.composetest.screens.stt

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sindorim.composetest.ui.theme.nanumSquareNeo

@ExperimentalPermissionsApi
@Composable
fun SpeechScreen(navController: NavController) {
    val context = LocalContext.current
    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    val transcription = remember { mutableStateOf("") }
    val isRecording = remember { mutableStateOf(false) }

    val speechPermissionState = rememberPermissionState(
        Manifest.permission.RECORD_AUDIO
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (speechPermissionState.status.isGranted) {
                        if (!isRecording.value) {
                            isRecording.value = true
                            startListening(speechRecognizer, transcription)
                        } else {
                            isRecording.value = false
                            stopListening(speechRecognizer)
                        }
                    } else {
                        speechPermissionState.launchPermissionRequest()
                    }
                }
            ) {
                Text(if (!isRecording.value) "Start" else "Stop")
            }
            Text(
                text = transcription.value,
                color = Color.Black,
                fontFamily = nanumSquareNeo,
                fontWeight = FontWeight.Bold,
            )
        }
    } //
} // End of SpeechScreen

fun startListening(
    speechRecognizer: SpeechRecognizer,
    transcription: MutableState<String>
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        // Set the language to Korean
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
    }

    val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(p0: Float) {}
        override fun onBufferReceived(p0: ByteArray?) {}
        override fun onEndOfSpeech() {
            Log.d("SDR", "onEndOfSpeech: ")
        }
        override fun onError(p0: Int) {}

        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.d("SDR", "onResults: $matches")
            if (matches != null && matches.isNotEmpty()) {
                transcription.value = matches[0]
            }
        }

        override fun onPartialResults(p0: Bundle?) {
            TODO("Not yet implemented")
        }

        override fun onEvent(p0: Int, p1: Bundle?) {
            TODO("Not yet implemented")
        }
    }

    speechRecognizer.setRecognitionListener(recognitionListener)
    speechRecognizer.startListening(intent)
}

fun stopListening(speechRecognizer: SpeechRecognizer) {
    speechRecognizer.stopListening()
}