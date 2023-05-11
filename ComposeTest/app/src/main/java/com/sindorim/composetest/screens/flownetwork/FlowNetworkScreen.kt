package com.sindorim.composetest.screens.flownetwork

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sindorim.composetest.data.NetworkResult
import com.sindorim.composetest.data.dto.PeopleResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private const val TAG = "FlowNetworkScreen_SDR"

@Composable
fun FlowNetworkScreen(
    navController: NavController, viewModel: FlowNetworkViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val peopleList by viewModel.peopleList.collectAsState()

    val intState = remember { mutableStateOf(100) }

    uiState.let {
        when (it) {
            is NetworkResult.Success -> {
                intState.value = 200
            }
            is NetworkResult.Loading -> {
                intState.value = 100
            }
            else ->{
                intState.value = 400
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.TopCenter
            ) {
                Button(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.fetchData()
                    }
                }) {
                    Text(text = "Log")
                }
            }
            if (intState.value == 100) {
                Log.d(TAG, "if-else1: ${uiState.data}")
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (intState.value == 200) {
                Log.d(TAG, "if-else2: $uiState")
                LazyColumn() {
                    items(peopleList) { people ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = people.name)
                                Text(text = people.gender)
                            }
                        }
                    }
                }
            } else {
                Text("ERROR")
            }

        }
    }

}