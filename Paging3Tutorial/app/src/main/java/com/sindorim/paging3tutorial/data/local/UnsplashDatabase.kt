package com.sindorim.paging3tutorial.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sindorim.paging3tutorial.data.local.dao.UnsplashImageDao
import com.sindorim.paging3tutorial.data.local.dao.UnsplashRemoteKeysDao
import com.sindorim.paging3tutorial.model.UnsplashImage
import com.sindorim.paging3tutorial.model.UnsplashRemoteKeys

@Database(entities = [UnsplashImage::class, UnsplashRemoteKeys::class], version = 1, exportSchema = false)
abstract class UnsplashDatabase: RoomDatabase() {
    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao
}