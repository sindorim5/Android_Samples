package com.sindorim.composetest.data.dto

import com.google.gson.annotations.SerializedName

data class PeopleResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    @SerializedName("results")
    val peopleList: List<People>
)