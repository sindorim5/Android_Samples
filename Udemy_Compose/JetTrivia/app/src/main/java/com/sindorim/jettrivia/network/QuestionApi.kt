package com.sindorim.jettrivia.network

import com.sindorim.jettrivia.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {

    @GET("world.json")
    suspend fun getAllQuestions(): Question

}