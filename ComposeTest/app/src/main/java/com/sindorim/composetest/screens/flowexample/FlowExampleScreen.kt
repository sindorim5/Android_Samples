package com.sindorim.composetest.screens.flowexample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun FlowExampleScreen(
    navController: NavController
) {
    val viewModel = viewModel<FlowExampleViewModel>()
//    val composeColor = viewModel.composeColor
    val flowColor by viewModel.color.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    .border(width = 1.dp, color = Color.Black)
                    .background(Color(flowColor))
                    .clickable {
                        viewModel.generateNewColor()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Flow Color", color = Color.Black)
            }

//            Box(
//                modifier = Modifier
//                    .width(200.dp)
//                    .height(100.dp)
//                    .border(width = 1.dp, color = Color.Black)
//                    .background(Color(composeColor))
//                    .clickable {
//                        viewModel.generateNewColor()
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Text("Compose Color", color = Color.Black)
//            }
        }
    }


}