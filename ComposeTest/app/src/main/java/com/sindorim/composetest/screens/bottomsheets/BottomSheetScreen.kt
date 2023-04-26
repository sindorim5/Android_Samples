package com.sindorim.composetest.screens.bottomsheets

import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sindorim.composetest.ui.theme.BottomSheetColor
import java.util.Locale.filter

@ExperimentalMaterialApi
@Composable
fun BottomSheetScreen(
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(
        
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 128.dp,
        sheetContent = { BottomSheet() },
        sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        sheetBackgroundColor = BottomSheetColor,
    ) {
        Text(text = "hi")
    }
}

@Composable
fun BottomSheet() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(128.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Swipe up to expand sheet")
    }
    Column(
        modifier = Modifier.padding(32.dp)
    ) {
        Text(
            text = "Bottom sheet",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Hi I am Bottom Sheet Content",
            style = MaterialTheme.typography.body1
        )
    }
}