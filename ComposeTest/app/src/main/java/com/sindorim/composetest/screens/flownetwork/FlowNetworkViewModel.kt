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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FlowNetworkViewModel_SDR"

@HiltViewModel
class FlowNetworkViewModel @Inject constructor(
    private val swRepository: SwRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            fetchData()
        }
    }

    suspend fun fetchData() {
        Log.d(TAG, "fetchData: fetchGO")
        swRepository.getAllPeople()
            .onStart {
                _uiState.value = NetworkResult.Loading()
                Log.d(TAG, "fetchData: ${uiState.value}")
            }
            .catch {response ->
                response.printStackTrace()
            }
            .flowOn(Dispatchers.IO)
            .collect { response ->
                Log.d(TAG, "fetchDataREP: $response")
//                    if (response.data != null) {
//                        Log.d(TAG, "init: ${response.data}")
//                        _uiState.value = response
//                    } else {
//                        Log.d(TAG, "error: ${response.message}")
//                    }
                when {
                    response.isSuccessful -> {
                        _uiState.value = NetworkResult.Success(response.body()!!)
                    }
                    response.errorBody() != null -> NetworkResult.Error(
                        response.errorBody()!!.string(),
                        null
                    )
                    else -> NetworkResult.Error(
                        response.errorBody()!!.string(),
                        null
                    )
                }
            }
    } // End of fetchData

    private val _uiState = MutableStateFlow<NetworkResult<PeopleResponse>>(NetworkResult.Loading())
    val uiState: StateFlow<NetworkResult<PeopleResponse>> = _uiState

    val peopleList = uiState.map {
        it.data?.peopleList ?: emptyList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

}