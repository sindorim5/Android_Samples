package com.sindorim.composetest

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sindorim.composetest.ui.theme.nanumSquareNeo
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Preview
@Composable
fun BottomSheetScreen3(
    navController: NavController = NavController(LocalContext.current)
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 72.dp,
        sheetContent = {
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
                        .padding(top = 16.dp, bottom = 8.dp),
                    label = "목적지",
                    content = "1층 남자 화장실"
                )
                Button(
                    onClick = {
                        scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                    }
                ) {
                    Text("안내 시작")
                }
            }
        } // End of Sheetcontent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Inner Contents")
        }
    }
} // End of BottomSheetScreen

@Preview(showBackground = true)
@Composable
fun BottomSheetTextItem(
    modifier: Modifier = Modifier,
    label: String = "현재 위치",
    content: String = "1층 입구"
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start

    ) {
        Text(
            text = label,
            color = Color.LightGray,
            fontFamily = nanumSquareNeo,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = content,
            color = Color.Black,
            fontFamily = nanumSquareNeo,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
    }
}