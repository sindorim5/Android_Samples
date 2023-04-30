package com.sindorim.composetest

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sindorim.composetest.ui.theme.nanumSquareNeo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun ModalBottomSheetScreen3(
    navController: NavController
) {
    var openBottomSheet = rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = skipPartiallyExpanded
        skipPartiallyExpanded = true
    )

    val countdownTime = 3_000L
    val countdownText = remember { mutableStateOf("3") }

    LaunchedEffect(key1 = openBottomSheet.value) {
        if (openBottomSheet.value) {
            Log.d("SDR", "ModalBottomSheetScreen3: 11")
            for (i in 2 downTo 0) {
                delay(1000)
                countdownText.value = i.toString()
            }

            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    openBottomSheet.value = false
                }
                countdownText.value = "3"
            }
        } else {
            Log.d("SDR", "ModalBottomSheetScreen3: 22")
            countdownText.value = "3"
        }
    }

    // App content
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            Modifier.toggleable(
                value = skipPartiallyExpanded,
                role = Role.Checkbox,
                onValueChange = { checked -> skipPartiallyExpanded = checked }
            )
        ) {
            Checkbox(checked = skipPartiallyExpanded, onCheckedChange = null)
            Spacer(Modifier.width(16.dp))
            Text("Skip partially expanded State")
        }
        Button(onClick = { openBottomSheet.value = !openBottomSheet.value }) {
            Text(text = "Show Bottom Sheet")
        }
    } // End of App content Column

    // Sheet content
    if (openBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet.value = false },
            sheetState = bottomSheetState,
            modifier = Modifier
                .wrapContentHeight()
                .padding(bottom = 10.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(24.dp))
                BottomSheetTextItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(bottom = 8.dp),
                    label = "현재 위치",
                    content = "1층 입구"
                )
                Divider()
                BottomSheetTextItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 16.dp, bottom = 36.dp),
                    label = "목적지",
                    content = "1층 남자 화장실"
                )

                Text(
                    text = "${countdownText.value}초 뒤 자동으로 닫힘",
                    color = Color.Black,
                    fontFamily = nanumSquareNeo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
                Button(
                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                    // you must additionally handle intended state cleanup, if any.
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                openBottomSheet.value = false
                            }
                        }
                    }
                ) {
                    Text(
                        text = "안내 시작",
                        color = Color.White,
                        fontFamily = nanumSquareNeo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                } // End of Button
            } // End of Column
        } // End of ModalBottomSheet
    } // End of Sheet content
} // End of ModalBottomSheetScreen