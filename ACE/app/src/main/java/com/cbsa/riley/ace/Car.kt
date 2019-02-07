package com.cbsa.riley.ace

import com.beust.klaxon.Json

data class ManufacturerModel(
    val data: ArrayList<Any>
)

data class Car(
    @Json(name = "manufacturerNameId") val makeId: String,
    @Json(name = "manufacturerName") val make: String,
    @Json(name = "modelNameId") val modelId: String,
    @Json(name = "modelName") val model: String,
    val yearId: String,
    val year: String,
    val carImageId: String,
    val carImageURI: String,
    val exteriorImage: Boolean,
    val active: Boolean
)

data class Hotspot(
    val carImageId: String,
    val xLoc: Int,
    val yLoc: Int,
    val hotspotId: Int
)

data class basicCar(
    val make: String,
    val model: String,
    val year: String
)

