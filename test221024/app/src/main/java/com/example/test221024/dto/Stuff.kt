package com.example.test221024.dto

import java.io.Serializable

data class Stuff(
    var id: Int? = null,
    var name: String = "",
    var quantity: Int = 0
) : Serializable {
    override fun toString(): String {
        return "물품: $name -> 수량: $quantity"
    }
}
