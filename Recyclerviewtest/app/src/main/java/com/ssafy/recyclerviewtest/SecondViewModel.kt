package com.ssafy.recyclerviewtest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.recyclerviewtest.database.NoteDetailDao
import com.ssafy.recyclerviewtest.database.NoteDetailDto
import com.ssafy.recyclerviewtest.database.NoteDto
import com.ssafy.recyclerviewtest.repository.NoteDetailRepository
import com.ssafy.recyclerviewtest.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "SecondViewModel_싸피"
class SecondViewModel(var noteId: Long) : ViewModel() {

    private var noteRepository = NoteRepository.get()
    private var noteDetailRepository = NoteDetailRepository.get()

    val note = noteRepository.getNote(noteId)
    val originalDetailList = noteDetailRepository.getNoteDetailsbyNoteId(noteId)


    private var mSubNoteDetailList: MutableList<NoteDetailDto> = arrayListOf()
    private var _SubNoteDetailList = MutableLiveData<MutableList<NoteDetailDto>>()
    val liveNoteDetailList: LiveData<MutableList<NoteDetailDto>>
        get() = _SubNoteDetailList

    fun setNoteDetailList(list: MutableList<NoteDetailDto>) {
        mSubNoteDetailList.addAll(list)
        _SubNoteDetailList.value = mSubNoteDetailList
    }

    fun addNoteDetail() {
        mSubNoteDetailList.add(NoteDetailDto(noteId))
        Log.d(TAG, "addNoteDetail: $mSubNoteDetailList")
        _SubNoteDetailList.value = mSubNoteDetailList
    }

    fun saveData() {
        viewModelScope.launch {
            Log.d(TAG, "saveData: $mSubNoteDetailList")
            save()
            Log.d(TAG, "saveData: end")
        }
    }

    suspend fun save() {
        val job = viewModelScope.async {
            withContext(Dispatchers.IO) {
                for (item in mSubNoteDetailList) {
                    Log.d(TAG, "save: $item")
                    if (item.ID == 0L) {
                        Log.d(TAG, "save0: $item")
                        noteDetailRepository.insertNoteDetails(NoteDetailDto(noteId, item.CONTENTS))
                    } else {
                        if (item.CONTENTS.isBlank() || item.CONTENTS.isEmpty()) {
                            Log.d(TAG, "saveB: $item")
                            noteDetailRepository.deleteNoteDetailsById(item.ID)
                        } else {
                            Log.d(TAG, "saveU: $item")
                            noteDetailRepository.updateNoteDetail(item)
                        }
                    }
                }
            }
        }
        return job.await()
    }
}