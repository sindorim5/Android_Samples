package com.example.test221024.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.test221024.dto.Stuff
import java.sql.SQLException

private const val TAG = "StuffDao_싸피"
class StuffDao {
    // DB 선언부
    private lateinit var helper: DBHelper
    private lateinit var sqlDB: SQLiteDatabase
    private var mCtx: Context? = null
    private val DB_NAME = "clean_store"
    private val TABLE_NAME = "Stuff"
    private val STUFF_NAME = "name"
    private val STUFF_QNT = "quantity"

    @Throws(SQLException::class)
    fun open() {
        helper = DBHelper(mCtx!!)
        sqlDB = helper.writableDatabase
        Log.d(TAG, "open: $sqlDB")
    }

    fun dbOpenHelper(context: Context) {
        mCtx = context
    }

    fun create() {
        helper.onCreate(sqlDB)
    }

    fun upgrade(oldVersion: Int, newVersion: Int) {
        helper.onUpgrade(sqlDB, 1, 2)
    }

    fun close( ) {
        sqlDB.close()
    }

    fun selectAllStuffs(): MutableList<Stuff> {
        return helper.selectAll()
    }

    fun getCount(): Int {
        return helper.selectAll().size
    }

    fun selectStuff(id: Int): Stuff {
        return helper.select(id)
    }

    fun insertStuff(stuff: Stuff): Long {
        return helper.insert(stuff)
    }

    fun updateStuff(stuff: Stuff): Int {
        return helper.update(stuff)
    }

    fun deleteStuff(id: Int): Int {
        return helper.delete(id)
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
        private lateinit var db: SQLiteDatabase

        override fun onCreate(db: SQLiteDatabase) {
            val sql: String =
                "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                        "_id INTEGER PRIMARY KEY autoincrement," +
                        "name TEXT, quantity INTEGER)"
            db.execSQL(sql)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }

        override fun onOpen(db: SQLiteDatabase) {
            super.onOpen(db)
            this.db = db
        }


        fun insert(stuff: Stuff): Long {
            val contentValues = ContentValues()
            contentValues.apply {
                put(STUFF_NAME, stuff.name)
                put(STUFF_QNT, stuff.quantity)
            }
            db.beginTransaction()
            val result = db.insert(TABLE_NAME, null, contentValues)
            if (result > 0) {
                db.setTransactionSuccessful()
            }
            db.endTransaction()
            return result
        }

        fun update(stuff: Stuff): Int {
            val contentValues = ContentValues()
            contentValues.apply {
                put(STUFF_NAME, stuff.name)
                put(STUFF_QNT, stuff.quantity)
            }
            db.beginTransaction()
            val result = db.update(TABLE_NAME, contentValues, "_id=?", arrayOf(stuff.id.toString()))
            if (result > 0) {
                db.setTransactionSuccessful()
            }
            db.endTransaction()
            return result
        }

        fun delete(id: Int): Int {
            db.beginTransaction()
            val result = db.delete(TABLE_NAME, "_id=?", arrayOf(id.toString()))
            if (result > 0) {
                db.setTransactionSuccessful()
            }
            db.endTransaction()
            return result
        }

        fun select(id: Int): Stuff {
            val columns = arrayOf("_id, name, quantity")
            val stuff = Stuff()
            db.query(TABLE_NAME, columns, "_id=?", arrayOf(id.toString()), null, null, null).use {
                if (it.moveToLast()) {
                    stuff.id = it.getInt(0)
                    stuff.name = it.getString(1)
                    stuff.quantity = it.getInt(2)
                }
            }
            return stuff
        }

        fun selectAll(): MutableList<Stuff> {
            val result: MutableList<Stuff> = arrayListOf()
            val columns = arrayOf("_id, name, quantity")
            db.query(TABLE_NAME, columns, null, null, null, null, null).use {
                while (it.moveToNext()) {
                    val stuff = Stuff(
                        it.getInt(0),
                        it.getString(1),
                        it.getInt(2)
                    )
                    result.add(stuff)
                }
            }
            return result
        }

    }
}