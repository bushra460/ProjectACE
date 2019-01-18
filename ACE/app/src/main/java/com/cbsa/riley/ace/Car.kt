package com.cbsa.riley.ace

import com.beust.klaxon.Json
import com.google.gson.internal.LinkedTreeMap

data class ManufacturerModel(
    val _embedded: LinkedTreeMap<Any,Any>
)

data class Car(
    @Json(name = "manufacturerName") val make: String?,
@Json(name = "modelName") val model: String?,
val year: String?
)



data class CarNEW(val Cars: ArrayList<Any>)

data class CarOLD(val make: String, val model: String, val year: String)