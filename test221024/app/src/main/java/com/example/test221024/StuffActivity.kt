package com.example.test221024

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test221024.adapter.StuffAdapter
import com.example.test221024.databinding.ActivityStuffBinding
import com.example.test221024.dto.Stuff
import com.example.test221024.service.BoundService

const val RESULT_DELETE = 100
private const val TAG = "StuffActivity_싸피"
class StuffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuffBinding
    private lateinit var stuffAdapter: StuffAdapter
    private lateinit var myService: BoundService
    private var stuffList: MutableList<Stuff> = arrayListOf()
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.MyLocalBinder
            myService = binder.getService()
            isBound = true
            refreshData()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stuffAdapter = StuffAdapter().apply {
            this.stuffList = this@StuffActivity.stuffList
            this.itemClickListener = object : StuffAdapter.ItemClickListener{
                override fun onClick(view: View, position: Int) {
                    if (isBound) {
                        val intent = Intent(this@StuffActivity, StuffEditActivity::class.java).apply {
                            putExtra("stuff", stuffList[position])
                        }
                        resultLauncher.launch(intent)
                    }
                }
            }
        }

        binding.stuffRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = stuffAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        binding.stuffAddBtn.setOnClickListener {
            val tempStuff = Stuff()
            val intent = Intent(this, StuffEditActivity::class.java).apply {
                putExtra("stuff", tempStuff)
            }
            resultLauncher.launch(intent)
        }

        Log.d(TAG, "onCreate: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        Intent(this, BoundService::class.java).apply {
            bindService(this, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
        unbindService(connection)
        isBound = false
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(item.itemId == 0) {
            val position = stuffAdapter.getPosition()
            stuffList[position].id?.let { myService.delete(it) }

            refreshData()
        }

        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.stuff_add_option) {
            val tempStuff = Stuff()
            val intent = Intent(this, StuffEditActivity::class.java).apply {
                putExtra("stuff", tempStuff)
            }
            resultLauncher.launch(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val stuff = it.data?.getSerializableExtra("stuff") as Stuff
            if (stuff.id == null) {
                myService.insert(stuff)
            } else {
                myService.update(stuff)
            }
        } else if (it.resultCode == RESULT_DELETE) {
            val stuff = it.data?.getSerializableExtra("stuff") as Stuff
            myService.delete(stuff.id!!)
        }
        refreshData()
    }

    private fun refreshData() {
        this@StuffActivity.stuffList = myService.selectAll()
        stuffAdapter.stuffList = this@StuffActivity.stuffList
        (binding.stuffRecyclerView.adapter as StuffAdapter).notifyDataSetChanged()
    }
}