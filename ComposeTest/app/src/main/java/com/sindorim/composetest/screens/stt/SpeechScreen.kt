package com.sindorim.composetest.screens.stt

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sindorim.composetest.ui.theme.Purple80
import com.sindorim.composetest.ui.theme.nanumSquareNeo

private const val TAG = "SDR"

@ExperimentalPermissionsApi
@Composable
fun SpeechScreen(
    navController: NavController,
    viewModel: SpeechViewModel = viewModel()
) {
    val context = LocalContext.current
    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
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
                        if (!viewModel.isRecording.value) {
                            viewModel.isRecording.value = true
                            startListening(speechRecognizer, viewModel)
                        } else {
                            viewModel.isRecording.value = false
                            stopListening(speechRecognizer)
                        }
                    } else {
                        speechPermissionState.launchPermissionRequest()
                    }
                }
            ) {
                Text(if (!viewModel.isRecording.value) "Start" else "Stop")
            }
            Text(
                text = viewModel.transcription.value,
                color = Color.Black,
                fontFamily = nanumSquareNeo,
                fontWeight = FontWeight.Bold,
            )

            if (viewModel.isRecording.value) {
                Dialog(
                    onDismissRequest = {
                        viewModel.isRecording.value = false
                        stopListening(speechRecognizer)
                    }
                ) {
                    Surface(
                        modifier = Modifier,
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        viewModel.transcription.value = ""
                        SpeechDialogContents(viewModel)
                    }
                }
            }
        } // End of Column
    } // End of Surface
} // End of SpeechScreen

fun startListening(
    speechRecognizer: SpeechRecognizer,
    viewModel: SpeechViewModel
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        // Set the language to Korean
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    }

    val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {
            Log.d(TAG, "onReadyForSpeech: ")
        }

        override fun onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech: ")
        }

        override fun onRmsChanged(p0: Float) {}
        override fun onBufferReceived(p0: ByteArray?) {
            Log.d(TAG, "onBufferReceived: ")
        }

        override fun onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech: ")
        }

        override fun onError(p0: Int) {
            Log.d(TAG, "onError: ")
        }

        override fun onPartialResults(partialResults: Bundle?) {
            val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.d(TAG, "onPartialResults1: ${matches}")
            if (matches != null && matches.isNotEmpty()) {
                viewModel.transcription.value = matches[0]
                Log.d(TAG, "onPartialResults2: ${viewModel.transcription.value}")
            }
        }

        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.d(TAG, "onResults: ")
            if (matches != null && matches.isNotEmpty()) {
                viewModel.transcription.value = matches[0]
            }
            viewModel.isRecording.value = false
        }

        override fun onEvent(p0: Int, p1: Bundle?) {}
    }

    speechRecognizer.setRecognitionListener(recognitionListener)
    speechRecognizer.startListening(intent)
}

fun stopListening(speechRecognizer: SpeechRecognizer) {
    speechRecognizer.stopListening()
}

@Composable
fun SpeechDialogContents(
    viewModel: SpeechViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "듣고 있습니다...",
            modifier = Modifier,
            fontFamily = nanumSquareNeo,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        Text(
            text = if (viewModel.transcription.value == "") {
                Log.d(TAG, "TEXT1: ${viewModel.transcription.value}")
                "..."
            } else {
                Log.d(TAG, "TEXT2: ${viewModel.transcription.value}")
                viewModel.transcription.value
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        CircularProgressIndicator(
            modifier = Modifier,
            color = Purple80,
        )
    }
}