package com.cbsa.riley.ace

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
    var make: String,
    val modelId: String?,
    var model: String,
    val yearId: String?,
    var year: String,

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
    val carId: Int?,
    val exteriorImage: Boolean
)

data class NewDataClassCarImage(
    val carImageId: Int,
    val carImageURI: String,
    val exteriorImage: Boolean,
    val carId: Int?
)









