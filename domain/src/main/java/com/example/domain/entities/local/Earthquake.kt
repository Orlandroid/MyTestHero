package com.example.domain.entities.local

data class Earthquake(
    val place: String,
    val magnitude: Double,
    val latitude: Double,
    val longitude: Double
)