package com.sindorim.paging3tutorial.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sindorim.paging3tutorial.util.Constants.UNSPLASH_IMAGE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = UNSPLASH_IMAGE_TABLE)
data class UnsplashImage(
    @PrimaryKey
    val id: String,
    @Embedded
    val urls: Urls,
    val likes: Int,
    @Embedded
    val user: User
)
