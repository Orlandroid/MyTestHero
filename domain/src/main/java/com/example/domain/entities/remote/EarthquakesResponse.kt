package com.example.domain.entities.remote

data class EarthquakesResponse(
    val features: List<Feature>,
    val metadata: Metadata,
    val type: String
)

data class Feature(
    val geometry: Geometry,
    val id: String,
    val properties: Properties,
    val type: String
)

data class Metadata(
    val api: String,
    val count: Int,
    val generated: Long,
    val limit: Int,
    val offset: Int,
    val status: Int,
    val title: String,
    val url: String
)

data class Geometry(
    val coordinates: List<Double>,
    val type: String
)

data class Properties(
    val alert: Any,
    val cdi: Any,
    val code: String,
    val detail: String,
    val dmin: Double,
    val felt: Any,
    val gap: Double,
    val ids: String,
    val mag: Double,
    val magType: String,
    val mmi: Any,
    val net: String,
    val nst: Int,
    val place: String?,
    val rms: Double,
    val sig: Int,
    val sources: String,
    val status: String,
    val time: Long,
    val title: String,
    val tsunami: Int,
    val type: String,
    val types: String,
    val tz: Any,
    val updated: Long,
    val url: String
)