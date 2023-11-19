package com.example.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.entities.local.EarthquakeDb


@Database(entities = [EarthquakeDb::class], version = 1, exportSchema = false)

abstract class MainDatabase : RoomDatabase() {

    abstract fun EarthquakeDao(): EarthquakeDao

}