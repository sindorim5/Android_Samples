package com.sindorim.jetnote.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sindorim.jetnote.R
import com.sindorim.jetnote.components.NoteButton
import com.sindorim.jetnote.components.NoteInputText
import com.sindorim.jetnote.data.NoteDataSource
import com.sindorim.jetnote.model.Note
import com.sindorim.jetnote.util.formatDate
import java.time.format.DateTimeFormatter

@ExperimentalComposeUiApi
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column() {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    Toast.makeText(context, "Nav Clicked", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu"
                    )
                }
            },
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            actions = {
                IconButton(onClick = {
                    Toast.makeText(context, "Action Clicked", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = "Icon"
                    )
                }
            },
            backgroundColor = Color(0xFFDADFE3)
        ) // End of TopAppBar

        // Content
        Column(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(
                modifier = Modifier.padding(
                    top = 9.dp,
                    bottom = 8.dp,
                ),
                text = title,
                label = "Title",
                onTextChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace() }) {
                        title = it
                    }
                })

            NoteInputText(
                modifier = Modifier.padding(
                    top = 9.dp,
                    bottom = 8.dp,
                ),
                text = description,
                label = "Add a note",
                onTextChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace() }) {
                        description = it
                    }
                })

            NoteButton(text = "Save",
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        // save, add to the list
                        onAddNote(Note(title = title, description = description))
                        title = ""
                        description = ""
                        Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                    }


                })
        } // End of Content Column

        Divider(modifier = Modifier.padding(10.dp))
        LazyColumn {
            items(notes) { note ->
                NoteRow(
                    note = note,
                    onNoteClicked = {
                        onRemoveNote(note)
                        Toast.makeText(context, "Note Removed", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        } // End of LazyColumn
    } // End of NoteScreen Column
} // End of NoteScreen

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit
) {
    Surface(
        modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(),
        color = Color(0xFFDFE6EB),
        elevation = 6.dp
    ) {
        Column(
            modifier
                .clickable {
                    onNoteClicked(note)
                    Log.d("SDR", "NoteRow: ${note.title}")
                }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = note.title, style = MaterialTheme.typography.subtitle2)
            Text(text = note.description, style = MaterialTheme.typography.subtitle1)
            Text(
                text = formatDate(note.entryDate.time),
                style = MaterialTheme.typography.caption
            )
        } // End of Column
    } // End of Surface
} // End of NoteRow

@Preview(showBackground = true)
@ExperimentalComposeUiApi
@Composable
fun NotesScreenPreview() {
    NoteScreen(
        notes = NoteDataSource().loadNotes(),
        onAddNote = {},
        onRemoveNote = {}
    )
} // End of NotesScreenPreview