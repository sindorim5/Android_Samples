package com.sindorim.jetnote.data

import com.sindorim.jetnote.model.Note

class NoteDataSource {
    fun loadNotes(): List<Note> {
        return listOf(
            Note(title = "A good day", description = "We went on a vacation by the lake"),
            Note(title = "Android Compose", description = "Working on Android Compose course today"),
            Note(title = "Keep at it...", description = "Sometimes things just happen"),
            Note(title = "A movie day1", description = "1Watching a movie with family today"),
            Note(title = "A movie day2", description = "2Watching a movie with family today"),
            Note(title = "A movie day3", description = "3Watching a movie with family today"),
            Note(title = "A movie day4", description = "4Watching a movie with family today"),
            Note(title = "A movie day5", description = "5Watching a movie with family today"),
            Note(title = "A movie day6", description = "6Watching a movie with family today"),
            Note(title = "A movie day7", description = "7Watching a movie with family")
        )
    }

}