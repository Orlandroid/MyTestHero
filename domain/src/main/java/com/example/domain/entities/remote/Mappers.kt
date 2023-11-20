package com.example.domain.entities.remote

import com.example.domain.entities.local.EarthquakeDb


fun Feature.toEarthquakeDb(): EarthquakeDb {
    return EarthquakeDb(
        place = properties.place ?: "",
        magnitude = properties.mag,
        latitude = geometry.coordinates[0],
        longitude = geometry.coordinates[1]
    )
}

fun List<Feature>.totoEarthquakeDbList() = map { it.toEarthquakeDb() }
