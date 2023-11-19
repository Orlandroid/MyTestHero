package com.example.presentation.ui

import com.example.domain.entities.local.Earthquake
import com.example.domain.entities.remote.Feature

fun Feature.toEarthquake(): Earthquake {
    return Earthquake(
        place = properties.place,
        magnitude = properties.mag,
        latitude = geometry.coordinates[0],
        longitude = geometry.coordinates[1]
    )
}

fun List<Feature>.toEarthquakes() = map { it.toEarthquake() }
