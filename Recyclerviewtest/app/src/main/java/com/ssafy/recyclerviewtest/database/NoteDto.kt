package com.ssafy.recyclerviewtest.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteDto(
    val TITLE: String = "title"
) {
    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0

    constructor(id: Long, title: String) : this(title) {
        this.ID = id
    }

    override fun toString(): String {
        return "NoteDto(ID=$ID, TITLE=$TITLE)"
    }


}


