package com.cbsa.riley.ace

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.imageview.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL



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
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            println("onwindowfocuschanged() arraylistsize:    ${newHotspotArray.size}")
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

            checkExterior()
            if (exterior) {
                setExteriorImage()
            } else {
                setInteriorImage()
                val tab = tabLayout.getTabAt(1)
                tab?.select()
            }
            refreshHotspotList()

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
                override fun onTabSelected(tab: TabLayout.Tab) {
                    numTab = tab.position
                    when (numTab) {
                        0 -> setExteriorImage()
                        else -> {
                            setInteriorImage()
                        }
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    fun setExteriorImage() {
        //fab.isEnabled = true
        setExterior()
        var index = 0
        println("Exterior view")
        imageArrayList.forEach {
            if (it.carId == selectedCar.carId) {
                if (it.exteriorImage) {
                    imageViewE.setImageResource(0)
                    hotspotImageViewE.setImageResource(0)
                    Picasso.get().load(it.carImageURI).into(imageViewE)
                    selectedImage = index
                    setHotspots()
                }
            }
            index += 1
        }
    }

    fun setInteriorImage() {
        //fab.isEnabled = false
        setExterior()
        var index = 0
        println("Interior view")
        imageArrayList.forEach {
            //**************UNCOMMENT WHEN ALL CARS HAVE INTERIOR IMAGES*****************
            //if (it.carId == selectedCar.carId) {
                if (!it.exteriorImage) {
                    imageViewE.setImageResource(0)
                    hotspotImageViewE.setImageResource(0)
                    Picasso.get().load(it.carImageURI).into(imageViewE)
                    selectedImage = index
                    setHotspots()
                }
            //}
            index += 1
        }
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

                hotspotImageViewE.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {

                            val x: Int = motionEvent.x.toInt()
                            val y: Int = motionEvent.y.toInt()
                            val bitmapWidth = 40
                            val bitmapHeight = 40
                            var i = 0

                            println("X location Tapped: " + motionEvent.x.toInt())
                            println("Y location Tapped: " + motionEvent.y.toInt())

                            while (i < selectedCarHotspots.size) {
                                if (selectedCarHotspots[i].carImageId == imageArrayList[selectedImage].carImageId) {
                                    val xLocCheck = selectedCarHotspots[i].xLoc
                                    val yLocCheck = selectedCarHotspots[i].yLoc

                                    if (x > xLocCheck - bitmapWidth && x < xLocCheck + bitmapWidth && y > yLocCheck - bitmapHeight && y < yLocCheck + bitmapHeight) {
                                        val xdistance = Math.abs(selectedCarHotspots[0].xLoc - x)
                                        val ydistance = Math.abs(selectedCarHotspots[0].yLoc - y)
                                        var distance = xdistance + ydistance
                                        var idx = 0
                                        for (c in 1 until selectedCarHotspots.size) {
                                            val cxdistance = Math.abs(selectedCarHotspots[c].xLoc - x)
                                            val cydistance = Math.abs(selectedCarHotspots[c].yLoc - y)
                                            val cdistance = cxdistance + cydistance
                                            if (cdistance < distance) {
                                                idx = c
                                                distance = cdistance
                                            }
                                        }
                                        val theNumber = selectedCarHotspots[idx].hotspotId!!
                                        //println("hotspot ID of chosen Hotspot: " + exteriorHotspotID[idx])
                                        //println("exteriorHotspotID array: $exteriorHotspotID")
                                        toHotspotDetails(theNumber)
                                        println("the number is: $theNumber")
                                    }
                                }
                                i++
                            }
                        }
                    }
                    return@OnTouchListener true
                })
            }
        }
        hotspotImageViewE.setImageBitmap(bitmap)
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

    fun refreshHotspotList() {
        fun workload(data: String) {
            val gson = Gson()
            val parse = JsonParser().parse(data)
            println("raw parsed data: $parse")
            hotspotArrayList.clear()
            val modelsArrayValue = JsonParser().parse((gson.toJson(parse))).asJsonObject
            val carImageArray = modelsArrayValue.get("carImage").asJsonArray
            carImageArray.forEach {
                val carImageObj = it.asJsonObject
                val carImageId = carImageObj.get("carImageId").asInt
                val exteriorImage = carImageObj.get("exteriorImage").asBoolean

                val hotspotArrayValue = carImageObj.get("hotspotLocations").asJsonArray
                hotspotArrayValue.forEach {
                    val hotspotObj = it.asJsonObject
                    val xLoc = hotspotObj.get("xLoc").asInt
                    val yLoc = hotspotObj.get("yLoc").asInt
                    val hotspotId = hotspotObj.get("hotspotId").asInt
                    val hotspotDesc = hotspotObj.get("hotspotDesc").asString
                    val hotspotDetailsArray = hotspotObj.get("hotspotDetails").asJsonArray

                    hotspotDetailsArray.forEach {
                        val hotspotDetailsObj = it.asJsonObject
                        val hotspotUri = hotspotDetailsObj.get("uri").asString
                        val hotspotNotes = hotspotDetailsObj.get("notes").asString

                        hotspotArrayList.add(
                            NewDataClassHotspot(
                                hotspotId,
                                xLoc,
                                yLoc,
                                hotspotDesc,
                                true,
                                carImageId,
                                hotspotUri,
                                hotspotNotes,
                                selectedCar.carId,
                                exteriorImage
                            )
                        )
                    }
                }
            }
            val selectedCarHotspots = ArrayList<NewDataClassHotspot>()
            hotspotArrayList.forEach {
                if (it.carId == selectedCar.carId) {
                    selectedCarHotspots.add(it)
                }
            }
            runOnUiThread { setHotspots() }

        }

        GlobalScope.launch {
            println("Car Image Id ${imageArrayList[selectedImage].carImageId}")
            val json =
                URL("https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/cars/${selectedCar.carId}").readText()
            workload(json)
        }
    }

    fun resizeForScreenSize(){
        val heightDefault = 1794.0f
        val widthdefault = 1080.0f
        val display = windowManager.defaultDisplay
        val width = display.width
        val height = display.height
        val heightDifference = height - heightDefault
        val widthDifference = width - widthdefault
        if (heightDifference > 0) {
            hotspotImageViewE.translationY = heightDifference/2.2f
        }
        if (widthDifference > 0) {
            hotspotImageViewE.translationX = widthDifference/2
        }
    }
}