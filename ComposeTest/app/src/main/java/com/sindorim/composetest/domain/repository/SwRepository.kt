package com.sindorim.composetest.domain.repository

import com.sindorim.composetest.data.NetworkResult
import com.sindorim.composetest.data.dto.PeopleResponse
import com.sindorim.composetest.di.AppModule
import com.sindorim.composetest.domain.api.SwApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SwRepository @Inject constructor(
    private val swApi: SwApi
) {

    suspend fun getAllPeople(): Flow<NetworkResult<PeopleResponse>> {
        return flow {
            swApi.getAllPeople()
        }
    } // End of getAllPeople

} // End of SwRepository

//private val _testCallStateFlow = MutableStateFlow<NetworkResult<Int>>(NetworkResult.Loading())
//val testCallStateFlow: StateFlow<NetworkResult<Int>> = _testCallStateFlow.asStateFlow()
//
//suspend fun testCall() {
//    val response = testApi.testCall()
//
//    _testCallStateFlow.value = NetworkResult.Loading()
//    when {
//        response.isSuccessful -> {
//            _testCallStateFlow.value = NetworkResult.Success(
//                response.code()
//            )
//        }
//        response.errorBody() != null -> {
//            _testCallStateFlow.value = NetworkResult.Error(
//                response.errorBody()!!.string()
//            )
//        }
//    }
//} // End of testCall
//
//suspend fun testCall2(): Flow<Response<Void>> = flow {
//    emit(testApi.testCall2())
//}.flowOn(Dispatchers.IO)  // End of testCall2