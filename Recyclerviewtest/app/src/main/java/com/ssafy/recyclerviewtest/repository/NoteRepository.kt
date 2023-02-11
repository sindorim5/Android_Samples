package com.ssafy.recyclerviewtest.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.withTransaction
import com.ssafy.recyclerviewtest.database.MainDatabase
import com.ssafy.recyclerviewtest.database.NoteDto

class NoteRepository(private val db : MainDatabase) {

    private val noteDao = db.noteDao()

    fun getAllNotes(): LiveData<MutableList<NoteDto>> {
        return noteDao.getAllNotes()
    }

    fun getNote(id: Long): LiveData<NoteDto> {
        return noteDao.getNote(id)
    }

    suspend fun insertNote(note: NoteDto) : Long {
        return noteDao.insertNote(note)
    }

    companion object{
        private var INSTANCE : NoteRepository? = null

        fun initialize(db: MainDatabase){
            if(INSTANCE == null){
                INSTANCE = NoteRepository(db)
            }
        }

        fun get() : NoteRepository {
            return INSTANCE ?:
            throw IllegalStateException("GoalRepository must be initialized")
        }
    }
}