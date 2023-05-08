package com.sindorim.composetest.screens.flownetwork

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sindorim.composetest.di.AppModule
import com.sindorim.composetest.domain.repository.SwRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FlowNetworkViewModel_SDR"

@HiltViewModel
class FlowNetworkViewModel @Inject constructor(
    @AppModule.OkHttpInterceptorApi private val swRepository: SwRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            val test = swRepository.getAllPeople()
            Log.d(TAG, "test: $test")
        }
    }

}