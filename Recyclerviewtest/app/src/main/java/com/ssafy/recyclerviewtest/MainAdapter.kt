package com.ssafy.recyclerviewtest

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.recyclerviewtest.database.NoteDetailDto
import com.ssafy.recyclerviewtest.database.NoteDto

private const val TAG = "MainAdapter_싸피"
class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    var noteDetailList = mutableListOf<NoteDetailDto>()

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var editText : EditText = itemView.findViewById(R.id.sub_goal_et)

        fun bindInfo(noteDetailDto: NoteDetailDto) {
            editText.setText(noteDetailDto.CONTENTS)
            editText.addTextChangedListener( object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    noteDetailList[adapterPosition].CONTENTS = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holder, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindInfo(noteDetailList[position])
    }

    override fun getItemCount(): Int {
        return noteDetailList.size
    }
}