package com.sindorim.composetest.screens.search

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sindorim.composetest.ui.theme.Purple80
import com.sindorim.composetest.ui.theme.iconColor
import com.sindorim.composetest.ui.theme.nanumSquareNeo

private const val TAG = "SearchComponents_SDR"

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun InputField(
    value: String,
    placeholder: String = "",
    onValueChanged: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit = {},
    keyboardActions: KeyboardActions,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val focusRequester = remember { FocusRequester() }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(64.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    onActiveChange(true)
                }
            }
            .semantics {
                onClick {
                    focusRequester.requestFocus()
                    true
                }
            }
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
                value = value,
                onValueChange = { newValue ->
                    onValueChanged(newValue)
                },
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .padding(4.dp),
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
                keyboardActions = keyboardActions,
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
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                interactionSource = interactionSource
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