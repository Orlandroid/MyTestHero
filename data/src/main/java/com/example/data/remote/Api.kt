package com.example.data.remote


import com.example.domain.entities.remote.EarthquakesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    @GET("query")
    suspend fun getEarthquakes(
        @Query("format") format: String = "geojson",
        @Query("limit") limit: String = "12",
        @Query("starttime") startDate: String,
        @Query("endtime") endDate: String

    ): EarthquakesResponse

}