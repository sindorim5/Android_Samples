package com.sindorim.composetest.screens.flownetwork

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

private const val TAG = "FlowNetworkScreen_SDR"
@Composable
fun FlowNetworkScreen(
    navController: NavController, viewModel: FlowNetworkViewModel
) {


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val peopleList by viewModel.peopleList.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Button(onClick = {
                Log.d(TAG, "uiState: ${viewModel.uiState.value.data}")
                Log.d(TAG, "peopleList: ${viewModel.peopleList.value}")
            }) {
                Text(text = "Log")
            }
            LazyColumn() {
                items(peopleList) { people ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = people.name)
                            Text(text = people.gender)
                        }
                    }
                }
            }
        }
    }

}