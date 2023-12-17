package com.sindorim.paging3tutorial.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.sindorim.paging3tutorial.navigation.Screen
import com.sindorim.paging3tutorial.screens.common.ListContent

@ExperimentalMaterial3Api
@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val getAllImages = homeViewModel.getAllImages.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navHostController.navigate(Screen.Search.route)
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it).fillMaxSize()
        ) {
            ListContent(items = getAllImages)
        }
    }

}