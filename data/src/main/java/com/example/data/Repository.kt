package com.example.data


import com.example.data.db.EarthquakeDao
import com.example.domain.RemoteDataSource
import com.example.domain.entities.remote.EarthquakesResponse
import com.example.domain.entities.remote.totoEarthquakeDbList
import javax.inject.Inject


class Repository @Inject constructor(
    private val earthquakeDao: EarthquakeDao,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getEarthquakes(startDate: String, endDate: String): EarthquakesResponse {
        val data = remoteDataSource.getEarthquakes(startDate = startDate, endDate = endDate)
        earthquakeDao.deleteAll()
        earthquakeDao.insertAll(data.features.totoEarthquakeDbList())
        return data
    }

    suspend fun getAllEarthquake() = earthquakeDao.getAllEarthquake()

}