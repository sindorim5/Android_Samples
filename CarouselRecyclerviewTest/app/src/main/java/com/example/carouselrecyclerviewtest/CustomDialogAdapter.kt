package com.example.carouselrecyclerviewtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Date

class CustomDialogAdapter(var itemList : MutableList<String>) : RecyclerView.Adapter<CustomDialogAdapter.CustomDialogViewHolder>() {

    inner class CustomDialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle : TextView = itemView.findViewById(R.id.item_textview)

        fun bindInfo(item: String) {
            itemTitle.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomDialogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dialog_item, parent, false)
        return CustomDialogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CustomDialogViewHolder, position: Int) {
        holder.bindInfo(itemList[position])
    }

}