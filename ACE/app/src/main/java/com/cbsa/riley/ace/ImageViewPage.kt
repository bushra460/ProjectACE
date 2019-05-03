package com.cbsa.riley.ace

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.imageview.*



var exterior = true
val SHAREDPREFS = "com.cbsa.riley.ace"
var selectedCar = carArray[0]
var hotspotArrayList = ArrayList<NewDataClassHotspot>()
var imageArrayList = ArrayList<NewDataClassCarImage>()
var selectedImage = 0
var carValue = ""

class ImageViewPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageview)
        calibratePicture()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            var numTab = 0
            var index = 0
            val carId = intent.getIntExtra("carId", 0)
            carArray.forEach {
                val car = it
                if (carId == car.carId) {
                    selectedCar = carArray[index]
                }
                index += 1
            }

            hotspotArrayList = selectedCar.hotspotArrayList!!
            imageArrayList = selectedCar.imageArrayList!!

            resizeForScreenSize()

            val picsArrayX = ArrayList<Int>()
            imageArrayList.forEach {
                if (it.carId == selectedCar.carId){
                    if (it.exteriorImage)
                    picsArrayX.add(it.carId)
                }
            }

            val picsArray = ArrayList<Int>()
            imageArrayList.forEach {
                if (it.carId == selectedCar.carId){
                    if (!it.exteriorImage)
                        picsArray.add(it.carId)
                }
            }


            var imageNumberX = 2
            var imageNumber = 2
            pictureCount.text = "1 of " + picsArrayX.size
            rotateFAB.setOnClickListener {
                if (exterior) {
                    setExteriorImage(selectedImage+1)
                    if (imageNumberX == picsArrayX.size+1){
                        imageNumberX = 1
                    }
                    val arraySize = picsArrayX.size
                    val count = "$imageNumberX of $arraySize"
                    pictureCount.text = count
                    imageNumberX++
                } else if (!exterior) {



                    setInteriorImage(selectedImage+1)
                    if (imageNumber == picsArray.size+1){
                        imageNumber = 1
                    }
                    val arraySize = picsArray.size
                    val count = "$imageNumber of $arraySize"
                    pictureCount.text = count
                    imageNumber++
                }
            }

            checkExterior()
            if (exterior) {
                setExteriorImage(selectedImage)
            } else {
                setInteriorImage(selectedImage)
                val tab = tabLayout.getTabAt(1)
                tab?.select()
            }
            //refreshHotspotList()

            val carMake = selectedCar.make
            val carModel = selectedCar.model
            val carYear = selectedCar.year
            carValue = "$carMake $carModel $carYear"
            imageViewToolbar.title = carValue
            println(carValue)

            fab.setOnClickListener {
                setExterior()
                val intent = Intent(this, AddHotspotPage::class.java)
                if (numTab == 0) {
                    intent.putExtra("exterior", true)
                    startActivity(intent)
                } else {
                    intent.putExtra("exterior", false)
                    startActivity(intent)
                }
            }

            //HANDLE LISTVIEW BUTTON CLICKS
            val listViewBttn: Button = listViewBttn
            listViewBttn.setOnClickListener {
                val intent = Intent(this, ListViewPage::class.java)
                startActivity(intent)
            }

            val tabLayout = tabLayout
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                @SuppressLint("SetTextI18n")
                override fun onTabSelected(tab: TabLayout.Tab) {
                    numTab = tab.position
                    when (numTab) {
                        0 -> {setExteriorImage(selectedImage)
                        pictureCount.text = "1 of ${picsArrayX.size}"
                        }
                        else -> {
                            setInteriorImage(selectedImage)
                            pictureCount.text = "1 of ${picsArray.size}"
                        }
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    fun setExteriorImage(imageIndex: Int) {
        setExterior()
        var index = imageIndex
        var imageSet = false
        do {
            if (index == imageArrayList.size){
                index = 0
            }
            if (imageArrayList[index].carId == selectedCar.carId) {
                if (imageArrayList[index].exteriorImage) {
                    imageViewE.setImageResource(0)
                    hotspotImageViewE.setImageResource(0)
                    Picasso.get().load(imageArrayList[index].carImageURI).into(imageViewE)
                    selectedImage = index
                    imageSet = true
                    this.setHotspots()
                }
            }
            index += 1
        } while (!imageSet)
    }

    fun setInteriorImage(imageIndex: Int) {
        setExterior()
        var index = imageIndex
        var imageSet = false
        do {
            if (index == imageArrayList.size){
                index = 0
            }
            if (imageArrayList[index].carId == selectedCar.carId) {
                if (!imageArrayList[index].exteriorImage) {
                    imageViewE.setImageResource(0)
                    hotspotImageViewE.setImageResource(0)
                    Picasso.get().load(imageArrayList[index].carImageURI).into(imageViewE)
                    selectedImage = index
                    imageSet = true
                    this.setHotspots()
                }
            }

            if (index != imageIndex && !imageSet){
                imageViewE.setImageResource(0)
                hotspotImageViewE.setImageResource(0)
                Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/No_image_available_600_x_450.svg/600px-No_image_available_600_x_450.svg.png").into(imageViewE)
            }

            index += 1

        } while (index != imageIndex && !imageSet)
    }

    fun setHotspots() {
        val selectedCarHotspots = ArrayList<NewDataClassHotspot>()
        hotspotArrayList.forEach {
            if (it.carId == selectedCar.carId){
                selectedCarHotspots.add(it)
            }
        }
        val bitmap: Bitmap = Bitmap.createBitmap(hotspotImageViewE.width, hotspotImageViewE.height, Bitmap.Config.ARGB_8888)
        selectedCarHotspots.forEach {
            val hotspot = it
            val exteriorImagecheck = imageArrayList[selectedImage].carImageId
            if (exteriorImagecheck == hotspot.carImageId) {
                val canvas = Canvas(bitmap)
                val xLoc = hotspot.xLoc
                val yLoc = hotspot.yLoc
                val left = xLoc - 30.0f
                val top = yLoc + 30.0f
                val right = xLoc + 30.0f
                val bottom = yLoc - 30.0f
                val paint = Paint()
                val stroke = Paint()
                paint.color = Color.MAGENTA
                stroke.color = Color.RED
                stroke.style = Paint.Style.STROKE
                stroke.strokeWidth = 10.0f

                canvas.drawOval(left + 15, top - 15, right - 15, bottom + 15, paint)
                canvas.drawOval(left, top, right, bottom, stroke)
            }
        }
        hotspotImageViewE.setImageBitmap(bitmap)
        addSwipe()
    }

    fun checkExterior() {
        val prefs = getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE)
        exterior = prefs.getBoolean("exterior", true)
    }

    fun setExterior() {
        exterior = tabLayout.selectedTabPosition == 0
        getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE).edit().putBoolean("exterior", exterior).apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val detailsIntent = Intent(this, SearchPage::class.java)
        //val transitionManager = contentTransitionManager
        //window.enterTransition = Explode()

        exterior = true
        getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE).edit().putBoolean("exterior", exterior).apply()

        navigateUpTo(detailsIntent)
        carArray.clear()
    }

    fun toHotspotDetails(hotspotID: Int) {
        setExterior()
        val intent = Intent(this, ViewHotspotDetails::class.java)
        intent.putExtra("hotspotID", hotspotID)
        setExterior()
        startActivity(intent)
    }

//    fun refreshHotspotList() {
//        fun workload(data: String) {
//            val gson = Gson()
//            val parse = JsonParser().parse(data)
//            println("raw parsed data: $parse")
//            hotspotArrayList.clear()
//            val modelsArrayValue = JsonParser().parse((gson.toJson(parse))).asJsonObject
//            val carImageArray = modelsArrayValue.get("carImage").asJsonArray
//            carImageArray.forEach {
//                val carImageObj = it.asJsonObject
//                val carImageId = carImageObj.get("carImageId").asInt
//                val exteriorImage = carImageObj.get("exteriorImage").asBoolean
//
//                val hotspotArrayValue = carImageObj.get("hotspotLocations").asJsonArray
//                hotspotArrayValue.forEach {
//                    val hotspotObj = it.asJsonObject
//                    val xLoc = hotspotObj.get("xLoc").asInt
//                    val yLoc = hotspotObj.get("yLoc").asInt
//                    val hotspotId = hotspotObj.get("hotspotId").asInt
//                    val hotspotDesc = hotspotObj.get("hotspotDesc").asString
//                    val hotspotDetailsArray = hotspotObj.get("hotspotDetails").asJsonArray
//
//                    hotspotDetailsArray.forEach {
//                        val hotspotDetailsObj = it.asJsonObject
//                        val hotspotUri = hotspotDetailsObj.get("uri").asString
//                        val hotspotNotes = hotspotDetailsObj.get("notes").asString
//                        val hotspotDetailsActive = hotspotDetailsObj.get("active").asBoolean
//
//                        val hotspotDetailsArray = ArrayList<HotspotDeets>()
//                        val details = HotspotDeets(hotspotUri, hotspotNotes, hotspotDetailsActive)
//                        hotspotDetailsArray.add(details)
//
//                        hotspotArrayList.add(
//                            NewDataClassHotspot(
//                                hotspotId,
//                                xLoc,
//                                yLoc,
//                                hotspotDesc,
//                                true,
//                                carImageId,
//                                hotspotDetailsArray,
//                                selectedCar.carId,
//                                exteriorImage
//                            )
//                        )
//                    }
//                }
//            }
//            val selectedCarHotspots = ArrayList<NewDataClassHotspot>()
//            hotspotArrayList.forEach {
//                if (it.carId == selectedCar.carId) {
//                    selectedCarHotspots.add(it)
//                }
//            }
//            runOnUiThread { this.setHotspots() }
//
//        }
//
//        GlobalScope.launch {
//            println("Car Image Id ${imageArrayList[selectedImage].carImageId}")
//            val json =
//                URL("https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/cars/${selectedCar.carId}").readText()
//            workload(json)
//        }
//    }
}
