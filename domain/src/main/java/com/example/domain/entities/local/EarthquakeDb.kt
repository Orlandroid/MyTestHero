package com.example.domain.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EarthquakeDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val place: String,
    val magnitude: Double,
    val latitude: Double,
    val longitude: Double
)