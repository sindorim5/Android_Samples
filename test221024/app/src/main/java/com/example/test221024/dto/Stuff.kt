package com.example.test221024.dto

import java.io.Serializable

data class Stuff(
    var id: Int? = null,
    var name: String = "",
    var quantity: Int = 0
) : Serializable {
    override fun toString(): String {
        return "λ¬Όν: $name -> μλ: $quantity"
    }
}
