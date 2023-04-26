package com.sindorim.composetest.screens.search

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sindorim.composetest.ui.theme.iconColor
import com.sindorim.composetest.ui.theme.nanumSquareNeo

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val setValue = remember { mutableStateOf("init") }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                valueState = searchQueryState,
                onSpeak = {}
            ) { searchValue ->
                setValue.value = searchValue.trim()
            }

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

        }


    } // End of Surface
} // End of SearchScreen

@Preview(showBackground = true)
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun InputField(
    valueState: MutableState<String> = mutableStateOf(""),
    labelText: String = "목적지 입력",
    onSpeak: () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
//        border = BorderStroke(1.dp, Color.LightGray),
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
                leadingIcon = {
                    IconButton(
                        onClick = {
                            onSpeak()
                            Log.d("SDR", "InputField: MIC")
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