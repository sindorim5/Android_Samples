package com.sindorim.paging3tutorial.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.sindorim.paging3tutorial.screens.common.ListContent

@ExperimentalMaterial3Api
@ExperimentalPagingApi
@ExperimentalCoilApi
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    val searchedImages = searchViewModel.searchedImages.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(query = it)
                },
                onSearchClicked = {
                    searchViewModel.searchImages(query = it)
                },
                onCloseClicked = {
                    navHostController.popBackStack()
                }
            )
        },
    ) {
        Box(
            modifier = Modifier.padding(it).fillMaxSize()
        ) {
            ListContent(items = searchedImages)
        }
    }
}