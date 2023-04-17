package com.sindorim.jetweatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sindorim.jetweatherforecast.model.Favorite
import com.sindorim.jetweatherforecast.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}