package com.ssafy.recyclerviewtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ssafy.recyclerviewtest.database.NoteDetailDto
import com.ssafy.recyclerviewtest.database.NoteDto
import com.ssafy.recyclerviewtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mAdapter : MainAdapter

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.btnSecond.setOnClickListener {

            if (binding.note1Title.text.isEmpty()) {
                val intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra("noteId", 1L)
                }
                startActivity(intent)
            } else {
                var note1 = NoteDto(binding.note1Title.text.toString())
                var note1detail1 = NoteDetailDto(-1L, binding.note1Content1.text.toString())
                var note1detail2 = NoteDetailDto(-1L, binding.note1Content2.text.toString())
                var note1detail3 = NoteDetailDto(-1L, binding.note1Content3.text.toString())

                var note2 = NoteDto(binding.note2Title.text.toString())
                var note2detail1 = NoteDetailDto(-1L, binding.note2Content1.text.toString())
                var note2detail2 = NoteDetailDto(-1L, binding.note2Content2.text.toString())
                var note2detail3 = NoteDetailDto(-1L, binding.note2Content3.text.toString())

                mainViewModel.addNoteAndDetails(note1, mutableListOf(note1detail1, note1detail2, note1detail3))
                mainViewModel.addNoteAndDetails(note2, mutableListOf(note2detail1, note2detail2, note2detail3))

                val intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra("noteId", 1L)
                }
                startActivity(intent)
            }




        }
    }


}