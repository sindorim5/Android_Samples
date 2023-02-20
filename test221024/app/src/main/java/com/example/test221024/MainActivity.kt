package com.example.test221024

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test221024.adapter.MainAdapter
import com.example.test221024.databinding.ActivityMainBinding
import com.example.test221024.dto.Store

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val storeList: ArrayList<Store> = arrayListOf()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addData()
        mainAdapter = MainAdapter()
        mainAdapter.update(storeList)
        mainAdapter.itemClickListener = object : MainAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, StuffActivity::class.java)
                startActivity(intent)
            }
        }

        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mainAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun addData() {
        val store = Store(
            getString(R.string.main_store_name),
            getString(R.string.main_store_tel),
            getString(R.string.main_store_lat),
            getString(R.string.main_store_lng)
        )
        storeList.add(store)
    }

}