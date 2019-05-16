package com.cbsa.mcoe.ace.data_classes


// Holds full car from search page
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

        // Holds hotspot values from search page
        data class NewDataClassHotspot(
            val hotspotId: Int?,

            val xLoc: Int,
            val yLoc: Int,

            val hotspotDesc: String,
            val active: Boolean,

            val carImageId: Int,
            val hotspotDetails: ArrayList<HotspotDeets>,
            val carId: Int?,
            val exteriorImage: Boolean
        )

        // holds image values from search page (soon to be imageViewPage)
        data class NewDataClassCarImage(
            val carImageId: Int,
            val carImageURI: String,
            val exteriorImage: Boolean,
            val carId: Int?,
            val displayPic: Boolean
        )


// Used in both HotspotPost and NewDataClassHotspot
data class HotspotDeets(
    val uri: String,
    val notes: String,
    val active: Boolean
)



// USED IN HOTSPOT DETAILS PAGE
data class HotspotPost(
    val carImage: CarImage,
    val xLoc: Int,
    val yLoc: Int,
    val hotspotDesc: String,
    val active: Boolean,
    val hotspotDetails: ArrayList<HotspotDeets>
)
        data class CarImage(
            val carImageId: Int
        )



// USED IN HOTSPOT DETAILS PAGE
data class ImagePOST(
    val encodedImage: String,
    val imageName: String
)
