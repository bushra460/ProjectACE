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
    val carImageId: Int,
    val carImageURI: String,
    val exteriorImage: Boolean,
    val active: Boolean,
    val carId: Int
)

data class Hotspot(
    val carImageId: Int,
    val xLoc: Int,
    val yLoc: Int,
    val hotspotId: Int,
    val hotspotUri: String,
    val notes: String,
    val carId: Int?
)

data class BasicCar(
    val make: String,
    val model: String,
    val year: String
)

data class ImagePOST(
    val encodedImage: String,
    val imageName: String
)

data class CarImage(
    val carImageId: Int
)

data class HotspotPost(
    val carImage: CarImage,
    val xLoc: Int,
    val yLoc: Int,
    val hotspotDesc: String,
    val active: Boolean,
    val hotspotDetails: ArrayList<HotspotDeets>
)

data class HotspotDeets(
    val uri: String,
    val notes: String,
    val active: Boolean
)




data class NewDataClassCar(
    val carId: Int,
    val active: Boolean,

    val makeId: String?,
    val make: String,
    val modelId: String?,
    val model: String,
    val yearId: String?,
    val year: String,

    val imageArrayList: ArrayList<NewDataClassCarImage>?,
    val hotspotArrayList: ArrayList<NewDataClassHotspot>?
)

data class NewDataClassHotspot(
    val hotspotId: Int?,

    val xLoc: Int,
    val yLoc: Int,

    val hotspotDesc: String,
    val active: Boolean,

    val carImageId: Int,
    val hotspotUri: String,

    val notes: String?,
    val carId: Int?
)

data class NewDataClassCarImage(
    val carImageId: Int,
    val carImageURI: String,
    val exteriorImage: Boolean
)









