package com.ssafy.recyclerviewtest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE_NAME = "main.db"

@Database(entities = [NoteDto::class, NoteDetailDto::class], version = 1)
abstract class MainDatabase : RoomDatabase () {
    abstract fun noteDao() : NoteDao
    abstract fun noteDetailDao() : NoteDetailDao

    companion object{
        private var Instance: MainDatabase?=null
        fun getInstance(context: Context): MainDatabase {
            return Instance ?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    DATABASE_NAME
                ).build()
                Instance =instance
                instance
            }
        }
    }
}