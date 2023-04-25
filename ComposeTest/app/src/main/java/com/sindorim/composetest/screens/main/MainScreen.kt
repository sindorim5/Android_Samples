package com.sindorim.composetest

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sindorim.composetest.navigation.MyScreens

@Composable
fun MainScreen(
    navController: NavController
) {
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
            Button(
                onClick = {
                    navController.navigate(MyScreens.BottomSheetScreen.name)
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(text = "BottomSheetScreen", color = Color.White)
            }

            Button(
                onClick = {
                    navController.navigate(MyScreens.ModalBottomSheetScreen.name)
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(text = "ModalBottomSheetScreen", color = Color.White)
            }

            Button(
                onClick = {
                    navController.navigate(MyScreens.BottomSheetScreen3.name)
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(text = "BottomSheetScreen3", color = Color.White)
            }

            Button(
                onClick = {
                    navController.navigate(MyScreens.ModalBottomSheetScreen3.name)
                }
            ) {
                Text(text = "ModalBottomSheetScreen3", color = Color.White)
            }
        } // End of Column
    } // End of Surface
} // End of MainScreen