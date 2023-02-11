package com.ssafy.recyclerviewtest.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "note_detail",
    foreignKeys=[
        ForeignKey(
            entity = NoteDto::class,
            parentColumns= ["ID"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NoteDetailDto(
    var noteId: Long = -1L,
    var CONTENTS: String = "contents",
) {
    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0

    constructor(id: Long, noteId: Long, contents: String) : this(noteId, contents) {
        this.ID = id
    }

    override fun toString(): String {
        return "NoteDetailDto(ID=$ID, noteId=$noteId, CONTENTS=$CONTENTS)"
    }

}
