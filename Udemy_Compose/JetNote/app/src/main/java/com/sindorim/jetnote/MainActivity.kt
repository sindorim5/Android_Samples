package com.sindorim.jetnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sindorim.jetnote.data.NoteDataSource
import com.sindorim.jetnote.model.Note
import com.sindorim.jetnote.screen.NoteScreen
import com.sindorim.jetnote.screen.NoteViewModel
import com.sindorim.jetnote.ui.theme.JetNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val noteViewModel: NoteViewModel by viewModels()
                    NotesApp(noteViewModel = noteViewModel)
                } // End of Surface
            } // End of JetNoteTheme
        } // End of setContent
    } // End of onCreate
}

@ExperimentalComposeUiApi
@Composable
fun NotesApp(noteViewModel: NoteViewModel) {
    val noteList = noteViewModel.noteList.collectAsState().value

    NoteScreen(
        notes = noteList,
        onAddNote = { noteViewModel.addNote(it) },
        onRemoveNote = { noteViewModel.deleteNote(it) }
    )
}

@Preview(showBackground = true)
@ExperimentalComposeUiApi
@Composable
fun DefaultPreview() {
    JetNoteTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val noteViewModel = viewModel<NoteViewModel>()
            NotesApp(noteViewModel = noteViewModel)
        } // End of Surface
    } // End of JetNoteTheme
}