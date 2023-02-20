package com.example.test221024.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test221024.databinding.MainItemBinding
import com.example.test221024.dto.Store

class MainAdapter() : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private lateinit var storeList: ArrayList<Store>
    lateinit var itemClickListener: ItemClickListener

    inner class MainViewHolder(private val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store) {
            binding.mainStoreName.text = store.name
            binding.mainStoreTel.text = store.tel
            binding.mainStoreLat.text = store.lat
            binding.mainStoreLng.text = store.lng

            binding.mainItem.setOnClickListener {
                itemClickListener.onClick(it, layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(storeList[position])
    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun update(newStoreList: ArrayList<Store>) {
        this.storeList = newStoreList
    }

}