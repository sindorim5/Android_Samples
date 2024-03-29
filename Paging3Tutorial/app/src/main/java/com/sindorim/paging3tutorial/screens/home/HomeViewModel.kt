package com.sindorim.paging3tutorial.screens.home

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.sindorim.paging3tutorial.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class HomeViewModel
@Inject constructor(
    repository: Repository
): ViewModel() {
    val getAllImages = repository.getAllImages()
}