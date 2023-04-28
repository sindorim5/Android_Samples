package com.sindorim.composetest.screens.search

import android.Manifest
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
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

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current


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
                value = searchQueryState.value,
                placeholder = "목적지를 입력하세요",
                onValueChanged = { value ->
                    searchQueryState.value = value
                },
                keyboardActions = KeyboardActions(
                    onSearch = {
                        setValue.value = searchQueryState.value.trim()
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                leadingIcon = {
                    IconButton(
                        onClick = {
                            if (speechPermissionState.status.isGranted) {
                                if (!isRecording.value) {
                                    isRecording.value = true
                                    startListening(speechRecognizer, searchQueryState, isRecording)
                                }
                            }
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Speech To Text",
                            tint = iconColor
                        )
                    }
                }, // Mic Icon
                trailingIcon = {
                    IconButton(
                        onClick = {
                            setValue.value = searchQueryState.value.trim()
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
                } // Search Icon
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