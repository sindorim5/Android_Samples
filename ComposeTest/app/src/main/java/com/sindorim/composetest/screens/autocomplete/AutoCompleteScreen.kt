package com.sindorim.composetest.screens.autocomplete

import android.Manifest
import android.speech.SpeechRecognizer
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sindorim.composetest.screens.search.InputField
import com.sindorim.composetest.screens.search.SpeechDialogContent
import com.sindorim.composetest.screens.search.startListening
import com.sindorim.composetest.screens.search.stopListening
import com.sindorim.composetest.ui.theme.nanumSquareNeo
import kotlinx.coroutines.delay

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun AutoCompleteScreen(
    navController: NavController
) {
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    androidx.compose.material.Surface(
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

} // End of AutoCompleteScreen