package com.ssafy.recyclerviewtest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.recyclerviewtest.database.NoteDetailDto
import com.ssafy.recyclerviewtest.database.NoteDto
import com.ssafy.recyclerviewtest.repository.NoteDetailRepository
import com.ssafy.recyclerviewtest.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MainViewModel_싸피"
class MainViewModel : ViewModel() {

    private var noteRepository = NoteRepository.get()
    private var noteDetailRepository = NoteDetailRepository.get()

    private var id : Long = 0L

    fun addNoteAndDetails(noteDto: NoteDto, list: MutableList<NoteDetailDto>) {
        viewModelScope.launch {
            val noteId = addNote(noteDto)
            Log.d(TAG, "addNoteAndDetails: $noteId")
            addNoteDetail(noteId, list)
        }
    }

    fun setId(id: Long) {
        this.id = id
    }

    private suspend fun addNote(noteDto: NoteDto) : Long {
        val job = viewModelScope.async {
            withContext(Dispatchers.IO) {
                return@withContext noteRepository.insertNote(noteDto)
            }
        }
        return job.await()
    }

    private suspend fun addNoteDetail(noteId: Long, list: MutableList<NoteDetailDto>) {
        viewModelScope.launch {
            for (item in list) {
                item.noteId = noteId
                noteDetailRepository.insertNoteDetails(item)
            }
        }
    }

}