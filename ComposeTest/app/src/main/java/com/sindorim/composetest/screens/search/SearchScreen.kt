package com.sindorim.composetest.screens.search

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sindorim.composetest.ui.theme.Purple80
import com.sindorim.composetest.ui.theme.iconColor
import com.sindorim.composetest.ui.theme.nanumSquareNeo

private const val TAG = "SDR"

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(navController: NavController) {
    val searchQueryState = rememberSaveable { mutableStateOf("") }
    val setValue = remember { mutableStateOf("") }
    val isRecording = rememberSaveable { mutableStateOf(false) }

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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                valueState = searchQueryState,
                placeholder = "목적지를 입력하세요",
                onSpeak = {
                    if (speechPermissionState.status.isGranted) {
                        if (!isRecording.value) {
                            isRecording.value = true
                            startListening(speechRecognizer, searchQueryState, isRecording)
                        }
                    }
                },
                onSearch = { searchValue ->
                    setValue.value = searchValue.trim()
                    Log.d(TAG, "onSearch: ${setValue.value}")
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = searchQueryState.value,
                fontFamily = nanumSquareNeo,
                fontWeight = FontWeight.Light,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = setValue.value,
                fontFamily = nanumSquareNeo,
                fontWeight = FontWeight.Bold,
            )

            if (isRecording.value) {
                Dialog(
                    onDismissRequest = {
                        isRecording.value = false
                        stopListening(speechRecognizer)
                    }
                ) {
                    Surface(
                        modifier = Modifier,
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        searchQueryState.value = ""
                        SpeechDialogContent(transcription = searchQueryState)
                    }
                }
            } // Dialog if-state
        } // End of Column
    } // End of Surface
} // End of SearchScreen

@Preview(showBackground = true)
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun InputField(
    valueState: MutableState<String> = mutableStateOf(""),
    placeholder: String = "",
    onSpeak: () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(64.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(14.dp),
                clip = false,
                ambientColor = Color.LightGray,
                spotColor = Color.DarkGray
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = valueState.value,
                onValueChange = { valueState.value = it },
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(4.dp)
                    .fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontFamily = nanumSquareNeo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(valueState.value)
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color.LightGray,
                        fontFamily = nanumSquareNeo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                leadingIcon = {
                    IconButton(
                        onClick = {
                            onSpeak.invoke()
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Speech To Text",
                            tint = iconColor
                        )
                    }
                }, // End of leadingIcon
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onSearch(valueState.value)
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = iconColor
                        )
                    }
                } // End of trailingIcon
            ) // End of TextField
        } // End of Column
    } // End of Card
} // End of InputField

@Composable
fun SpeechDialogContent(
    transcription: MutableState<String>
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
            text = if (transcription.value == "") {
                Log.d(TAG, "if: ${transcription.value}")
                "..."
            } else {
                Log.d(TAG, "else: ${transcription.value}")
                transcription.value
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
    } // End of Column
} // End of SpeechDialogContent


fun startListening(
    speechRecognizer: SpeechRecognizer,
    transcription: MutableState<String>,
    isRecording: MutableState<Boolean>
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        // Set the language to Korean
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    }

    val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(p0: Float) {}
        override fun onBufferReceived(p0: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onError(p0: Int) {}

        override fun onPartialResults(partialResults: Bundle?) {
            val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.d(TAG, "onPartialResults1: ${matches}")
            if (matches != null && matches.isNotEmpty()) {
                transcription.value = matches[0]
                Log.d(TAG, "onPartialResults2: ${transcription.value}")
            }
        }

        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.d(TAG, "onResults: ")
            if (matches != null && matches.isNotEmpty()) {
                transcription.value = matches[0]
            }
            isRecording.value = false
        }

        override fun onEvent(p0: Int, p1: Bundle?) {}
    }

    speechRecognizer.setRecognitionListener(recognitionListener)
    speechRecognizer.startListening(intent)
} // End of startListening

fun stopListening(speechRecognizer: SpeechRecognizer) {
    speechRecognizer.stopListening()
} // End of stopListening