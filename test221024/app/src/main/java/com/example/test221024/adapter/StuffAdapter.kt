package com.example.test221024.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test221024.R
import com.example.test221024.dto.Stuff

class StuffAdapter : RecyclerView.Adapter<StuffAdapter.StuffViewHolder>() {

    lateinit var stuffList: MutableList<Stuff>
    private var rPosition = RecyclerView.NO_POSITION
    lateinit var itemClickListener: ItemClickListener

    inner class StuffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        var stuffItem: TextView = itemView.findViewById(R.id.stuff_item)

        fun bindInfo(stuff: Stuff) {
            stuffItem.text = stuff.toString()
            itemView.setOnClickListener {
                itemClickListener.onClick(it, layoutPosition)
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            rPosition = this.adapterPosition
            menu?.add(0, 0, 0, "삭제")
        }

    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StuffViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stuff_item, parent, false)
        return StuffViewHolder(view)
    }

    override fun onBindViewHolder(holder: StuffViewHolder, position: Int) {
        holder.bindInfo(stuffList[position])
    }

    override fun getItemCount(): Int {
        return stuffList.size
    }

    fun getPosition(): Int = rPosition
}