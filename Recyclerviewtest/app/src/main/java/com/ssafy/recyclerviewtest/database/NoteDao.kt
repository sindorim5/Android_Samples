package com.ssafy.recyclerviewtest.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAllNotes() : LiveData<MutableList<NoteDto>>

    @Query("SELECT * FROM note WHERE ID = (:id)")
    fun getNote(id: Long) : LiveData<NoteDto>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(dto : NoteDto) : Long

    @Update
    suspend fun updateNote(dto : NoteDto)

    @Delete
    suspend fun deleteNote(dto : NoteDto)

}