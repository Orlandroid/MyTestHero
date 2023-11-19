package com.example.domain

import com.example.domain.entities.remote.EarthquakesResponse


interface RemoteDataSource {

    suspend fun getEarthquakes(startDate: String, endDate: String): EarthquakesResponse
}