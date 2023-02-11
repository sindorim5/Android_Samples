package com.ssafy.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.recyclerviewtest.databinding.ActivitySecondBinding

private const val TAG = "SecondActivity_싸피"
class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var mAdapter: MainAdapter
    private lateinit var secondViewModel : SecondViewModel
    private var noteId : Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getLongExtra("noteId", -1L)

        secondViewModel = SecondViewModel(noteId)

        mAdapter = MainAdapter()

        secondViewModel.originalDetailList.observe(this) {
            Log.d(TAG, "original: $it")
            secondViewModel.setNoteDetailList(it)
        }

        secondViewModel.liveNoteDetailList.observe(this) {
            Log.d(TAG, "liveNoteDetailList: $it")
            mAdapter.noteDetailList = it
            mAdapter.notifyDataSetChanged()
        }

        binding.recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        binding.addEtBtn.setOnClickListener {
            secondViewModel.addNoteDetail()
        }

        binding.addSaveBtn.setOnClickListener {
            secondViewModel.liveNoteDetailList.removeObservers(this)
            secondViewModel.saveData()
            finish()
        }

    }
}