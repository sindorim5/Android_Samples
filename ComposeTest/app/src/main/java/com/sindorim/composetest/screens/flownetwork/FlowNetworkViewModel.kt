package com.sindorim.composetest.screens.flownetwork

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sindorim.composetest.data.NetworkResult
import com.sindorim.composetest.data.dto.People
import com.sindorim.composetest.data.dto.PeopleResponse
import com.sindorim.composetest.di.AppModule
import com.sindorim.composetest.domain.repository.SwRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FlowNetworkViewModel_SDR"

@HiltViewModel
class FlowNetworkViewModel @Inject constructor(
    private val swRepository: SwRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            swRepository.getAllPeople().collect { response ->
                Log.d(TAG, "init: ${response.data}")
                _uiState.value = response
            }
        }
    }

    private val _uiState = MutableStateFlow<NetworkResult<PeopleResponse>>(NetworkResult.Loading())
    val uiState: StateFlow<NetworkResult<PeopleResponse>> = _uiState

    val peopleList = uiState.map {
        it.data?.peopleList ?: emptyList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

}