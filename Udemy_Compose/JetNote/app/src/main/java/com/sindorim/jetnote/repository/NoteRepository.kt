package com.sindorim.jetnote.repository

import com.sindorim.jetnote.data.NoteDatabaseDao
import com.sindorim.jetnote.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatebaseDao: NoteDatabaseDao) {
    suspend fun addNote(note: Note) = noteDatebaseDao.insert(note)
    suspend fun updateNote(note: Note) = noteDatebaseDao.update(note)
    suspend fun deleteNote(note: Note) = noteDatebaseDao.deleteNote(note)
    suspend fun deleteAllNotes() = noteDatebaseDao.deleteAll()
    fun getAllNotes(): Flow<List<Note>> =
        noteDatebaseDao.getNotes().flowOn(Dispatchers.IO).conflate()
}