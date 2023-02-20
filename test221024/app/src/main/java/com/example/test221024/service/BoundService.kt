package com.example.test221024.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.test221024.dao.StuffDao
import com.example.test221024.dto.Stuff

private const val TAG = "BoundService_싸피"
class BoundService : Service() {
    private val stuffDao = StuffDao()

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: ")
        openDB()
        return mBinder
    }

    private fun openDB() {
        stuffDao.dbOpenHelper(this)
        stuffDao.open()
        initData(stuffDao.getCount())
    }

    private fun initData(count: Int) {
        var stuff1 = Stuff(null, "사과", 10)
        var stuff2 = Stuff(null, "참외", 11)
        if (count == 0) {
            stuffDao.insertStuff(stuff1)
            stuffDao.insertStuff(stuff2)
        }
    }

    fun insert(stuff: Stuff): Long {
        return stuffDao.insertStuff(stuff)
    }

    fun update(stuff: Stuff): Int {
        return stuffDao.updateStuff(stuff)
    }

    fun delete(id: Int): Int {
        return stuffDao.deleteStuff(id)
    }

    fun selectAll(): MutableList<Stuff> {
        return stuffDao.selectAllStuffs()
    }

    private val mBinder: IBinder = MyLocalBinder()

    inner class MyLocalBinder : Binder() {
        fun getService(): BoundService {
            return this@BoundService
        }
    }
}