package com.sindorim.composetest.domain.api

import com.sindorim.composetest.data.dto.PeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

interface SwApi {

    @GET("people")
    suspend fun getAllPeople(): Response<PeopleResponse>

}