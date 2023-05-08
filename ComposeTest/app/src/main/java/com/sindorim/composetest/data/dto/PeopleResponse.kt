package com.sindorim.composetest.data.dto

data class PeopleResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val peopleList: List<People>
)