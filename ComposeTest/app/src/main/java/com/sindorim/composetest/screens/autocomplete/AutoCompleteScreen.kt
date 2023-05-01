package com.sindorim.composetest.screens.autocomplete

import android.Manifest
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sindorim.composetest.screens.search.SpeechDialogContent
import com.sindorim.composetest.screens.search.startListening
import com.sindorim.composetest.screens.search.stopListening
import com.sindorim.composetest.ui.theme.IconColor
import com.sindorim.composetest.ui.theme.nanumSquareNeo

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun AutoCompleteScreen(
    navController: NavController
) {
    val searchQueryState = rememberSaveable { mutableStateOf("") }
    val dialogQueryState = remember { mutableStateOf("") }
    val setValue = remember { mutableStateOf("") }
    val autoCompleteSearchBarActiveState = remember { mutableStateOf(false) }
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
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            AutoCompleteSearchBar(
                value = searchQueryState.value,
                onValueChange = { value -> searchQueryState.value = value },
                active = autoCompleteSearchBarActiveState.value,
                onActiveChange = {
                    autoCompleteSearchBarActiveState.value = it
                    Log.d("SDR", "onActiveChange: ${autoCompleteSearchBarActiveState.value}")
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 8.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(14.dp),
                        clip = false,
                        ambientColor = Color.LightGray,
                        spotColor = Color.DarkGray
                    ),
                placeholder = "목적지를 입력하세요",
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
                            } else {
                                speechPermissionState.launchPermissionRequest()
                            }
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Speech To Text",
                            tint = IconColor
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
                            tint = IconColor
                        )
                    }
                } // Search Icon
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().background(Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(
                        persons.map { person ->
                            person.name
                        }.filter { name ->
                            name.lowercase().contains(searchQueryState.value.lowercase())
                        }.sorted()
                    ) { name ->
                        ListItem(
                            headlineContent = {
                                Text(text = name)
                            },
                            modifier = Modifier.clickable {
                                searchQueryState.value = name
                                autoCompleteSearchBarActiveState.value = false
                            }.background(Color.Transparent)
                        )
                    } // End of items
                } // End of LazyColumn
            } // End of AutoCompleteSearchBar
        }

        Column(
            modifier = Modifier
                .fillMaxWidth().padding(top = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Search: ${searchQueryState.value}",
                fontFamily = nanumSquareNeo,
                fontWeight = FontWeight.Light,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Set: ${setValue.value}",
                fontFamily = nanumSquareNeo,
                fontWeight = FontWeight.Bold,
            )

            if (isRecording.value) {
                Dialog(
                    onDismissRequest = {
                        isRecording.value = false
                        searchQueryState.value = dialogQueryState.value
                        stopListening(speechRecognizer)
                    }
                ) {
                    androidx.compose.material.Surface(
                        modifier = Modifier,
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        dialogQueryState.value = ""
                        SpeechDialogContent(transcription = dialogQueryState)
                    }
                }
            } // Dialog if-state

        } // End of Column

    } // End of Surface

} // End of AutoCompleteScreen