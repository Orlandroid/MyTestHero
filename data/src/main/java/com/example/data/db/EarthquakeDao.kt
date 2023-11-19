package com.example.data.db

import androidx.room.*
import com.example.domain.entities.local.EarthquakeDb


@Dao
interface EarthquakeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(earthquakes: List<EarthquakeDb>): List<Long>

    @Query("SELECT * FROM EarthquakeDb")
    suspend fun getAllEarthquake(): List<EarthquakeDb>

    @Query("DELETE  FROM EarthquakeDb")
    suspend fun deleteAll()
}