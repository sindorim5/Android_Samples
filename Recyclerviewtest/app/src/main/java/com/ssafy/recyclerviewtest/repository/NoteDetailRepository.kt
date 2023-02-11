package com.ssafy.recyclerviewtest.repository

import androidx.lifecycle.LiveData
import com.ssafy.recyclerviewtest.database.MainDatabase
import com.ssafy.recyclerviewtest.database.NoteDetailDao
import com.ssafy.recyclerviewtest.database.NoteDetailDto

class NoteDetailRepository(private val db : MainDatabase) {

    private val noteDetailDao = db.noteDetailDao()

    fun getAllNoteDetails() : LiveData<MutableList<NoteDetailDto>> {
        return noteDetailDao.getAllNoteDetails()
    }

    fun getNoteDetailsbyNoteId(noteId: Long) : LiveData<MutableList<NoteDetailDto>> {
        return noteDetailDao.getNoteDetailsbyNoteId(noteId)
    }

    suspend fun insertNoteDetails(dto: NoteDetailDto) {
        return noteDetailDao.insertNoteDetail(dto)
    }

    fun deleteNoteDetailsById(id: Long) {
        return noteDetailDao.deleteNoteDetailbyId(id)
    }

    suspend fun updateNoteDetail(dto: NoteDetailDto) {
        return noteDetailDao.updateNoteDetail(dto)
    }

    companion object{
        private var INSTANCE : NoteDetailRepository? = null

        fun initialize(db: MainDatabase){
            if(INSTANCE == null){
                INSTANCE = NoteDetailRepository(db)
            }
        }

        fun get() : NoteDetailRepository {
            return INSTANCE ?:
            throw IllegalStateException("GoalRepository must be initialized")
        }
    }

}