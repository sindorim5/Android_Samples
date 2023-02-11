package com.ssafy.recyclerviewtest.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDetailDao {

    @Query("SELECT * FROM note_detail")
    fun getAllNoteDetails() : LiveData<MutableList<NoteDetailDto>>

    @Query("SELECT * FROM note_detail WHERE noteId = (:noteId)")
    fun getNoteDetailsbyNoteId(noteId: Long) : LiveData<MutableList<NoteDetailDto>>

    @Query("DELETE FROM note_detail WHERE ID = (:id)")
    fun deleteNoteDetailbyId(id: Long)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteDetail(dto: NoteDetailDto)

    @Update
    suspend fun updateNoteDetail(dto: NoteDetailDto)

    @Delete
    suspend fun deleteNote(dto: NoteDetailDto)

}