package com.ssafy.recyclerviewtest

import android.app.Application
import com.ssafy.recyclerviewtest.database.MainDatabase
import com.ssafy.recyclerviewtest.repository.NoteDetailRepository
import com.ssafy.recyclerviewtest.repository.NoteRepository

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        val db by lazy{ MainDatabase.getInstance(this) }
        NoteRepository.initialize(db)
        NoteDetailRepository.initialize(db)
    }
}