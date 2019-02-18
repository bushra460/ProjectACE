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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.imageview.*

var exterior = true
val SHAREDPREFS = "com.cbsa.riley.ace"
var carIndex = 0
var selectedCar = newCarArray[0]
var hotspotArrayList = ArrayList<NewDataClassHotspot>()
var imageArrayList = ArrayList<NewDataClassCarImage>()


class ImageViewPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageview)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            var numTab: Int = 0
            var index = 0
            val carId = intent.getIntExtra("carId", 0)
            newCarArray.forEach {
                if (carId == it.carId) {
                    carIndex = index
                }
                index += 1
            }
            selectedCar = newCarArray[carIndex]
            println(selectedCar)

            if (selectedCar.hotspotArrayList != emptyArray<Any>()){
                hotspotArrayList = selectedCar.hotspotArrayList!!
            }
            if (selectedCar.imageArrayList != emptyArray<Any>()){
                imageArrayList = selectedCar.imageArrayList!!
            }

            checkExterior()
            if (exterior) {
                setExteriorImage()
            } else {
                setInteriorImage()
            }

            val carMake = selectedCar.make
            val carModel = selectedCar.model
            val carYear = selectedCar.year

            imageViewToolbar.title = "$carMake $carModel $carYear"
            println("$carMake $carModel $carYear")

            fab.setOnClickListener {
                val intent = Intent(this, AddHotspotPage::class.java)
                intent.putExtra("imageArrayList", selectedCar.imageArrayList)
                intent.putExtra("hotspotArrayList", selectedCar.hotspotArrayList)
                if (numTab == 0) {
                    intent.putExtra("exterior", true)

                    startActivity(intent)
                } else {
                    intent.putExtra("exterior", false)

                    startActivity(intent)
                }
            }

            //HANDLE LISTVIEW BUTTON CLICKS
//        val listViewBttn: Button = listViewBttn
//        listViewBttn.setOnClickListener {
//
//            val intent = Intent(this, ListViewPage::class.java)
//
//            startActivity(intent)
//        }

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

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }
            })
        }
    }

    fun setExteriorImage() {
        println("Exterior view")
        println(imageArrayList)
        imageViewE.setImageResource(0)
        hotspotImageViewE.setImageResource(0)
        Picasso.get().load(imageArrayList[0].carImageURI).into(imageViewE)
        setHotspotsExterior()
    }

    fun setInteriorImage() {
        println("Interior view")
        imageViewE.setImageResource(0)
        hotspotImageViewE.setImageResource(0)
        //Picasso.get().load(interiorImageURIArray[0]).into(imageViewE)
        Picasso.get().load("https://via.placeholder.com/150").into(imageViewE)
        setHotspotsInterior()
    }

    fun setHotspotsExterior() {
        hotspotArrayList.forEach {

            val bitmap: Bitmap =
                Bitmap.createBitmap(hotspotImageViewE.width, hotspotImageViewE.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val xLoc = it.xLoc
            val yLoc = it.yLoc
            val left = xLoc - 30.0f
            val top = yLoc + 30.0f
            val right = xLoc + 30.0f
            val bottom = yLoc - 30.0f
            val paint = Paint()
            val stroke = Paint()
            paint.color = Color.YELLOW
            stroke.color = Color.RED
            stroke.style = Paint.Style.STROKE
            stroke.strokeWidth = 10.0f

            canvas.drawOval(left + 15, top - 15, right - 15, bottom + 15, paint)
            canvas.drawOval(left, top, right, bottom, stroke)

            hotspotImageViewE.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {

                        val x: Int = motionEvent.x.toInt()
                        val y: Int = motionEvent.y.toInt()
                        val bitmapWidth = 30
                        val bitmapHeight = 30
                        var i = 0

                        println("X location Tapped: " + motionEvent.x.toInt())
                        println("Y location Tapped: " + motionEvent.y.toInt())

                        while (i < imageArrayList.size) {
                            val xLocCheck = hotspotArrayList[i].xLoc
                            val yLocCheck = hotspotArrayList[i].yLoc

                            if (x > xLocCheck - bitmapWidth && x < xLocCheck + bitmapWidth && y > yLocCheck - bitmapHeight && y < yLocCheck + bitmapHeight) {
                                var distance = Math.abs(hotspotArrayList[0].xLoc - x)
                                var idx = 0
                                for (c in 1 until hotspotArrayList.size) {
                                    val cdistance = Math.abs(hotspotArrayList[c].xLoc - x)
                                    if (cdistance < distance) {
                                        idx = c
                                        distance = cdistance
                                    }
                                }
                                val theNumber = hotspotArrayList[idx]
                                //println("hotspot ID of chosen Hotspot: " + exteriorHotspotID[idx])
                                //println("exteriorHotspotID array: $exteriorHotspotID")
                                toHotspotDetails(hotspotArrayList[idx].toString())

                                println("the number is: $theNumber")

                            }
                            i++
                        }
                    }
                }
                return@OnTouchListener true
            })

            hotspotImageViewE.setImageBitmap(bitmap)
        }
    }

    fun setHotspotsInterior() {
        hotspotArrayList.forEach {
            val bitmap: Bitmap =
                Bitmap.createBitmap(hotspotImageViewE.width, hotspotImageViewE.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val xLoc = it.xLoc
            val yLoc = it.yLoc
            val left = xLoc - 30.0f
            val top = yLoc + 30.0f
            val right = xLoc + 30.0f
            val bottom = yLoc - 30.0f
            val paint = Paint()
            val stroke = Paint()
            paint.color = Color.YELLOW
            stroke.color = Color.RED
            stroke.style = Paint.Style.STROKE
            stroke.strokeWidth = 10.0f

            canvas.drawOval(left + 15, top - 15, right - 15, bottom + 15, paint)
            canvas.drawOval(left, top, right, bottom, stroke)

            hotspotImageViewE.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {

                        val x: Int = motionEvent.x.toInt()
                        val y: Int = motionEvent.y.toInt()
                        val bitmapWidth = 30
                        val bitmapHeight = 30
                        var i = 0

                        println("X location Tapped: " + motionEvent.x.toInt())
                        println("Y location Tapped: " + motionEvent.y.toInt())

                        while (i < imageArrayList.size) {
                            val xLocCheck = hotspotArrayList[i].xLoc
                            val yLocCheck = hotspotArrayList[i].yLoc

                            if (x > xLocCheck - bitmapWidth && x < xLocCheck + bitmapWidth && y > yLocCheck - bitmapHeight && y < yLocCheck + bitmapHeight) {
                                var distance = Math.abs(hotspotArrayList[0].xLoc - x)
                                var idx = 0
                                for (c in 1 until hotspotArrayList.size) {
                                    val cdistance = Math.abs(hotspotArrayList[c].xLoc - x)
                                    if (cdistance < distance) {
                                        idx = c
                                        distance = cdistance
                                    }
                                }
                                val theNumber = hotspotArrayList[idx]
                                //println("hotspot ID of chosen Hotspot: " + exteriorHotspotID[idx])
                                //println("exteriorHotspotID array: $exteriorHotspotID")
                                toHotspotDetails(hotspotArrayList[idx].toString())

                                println("the number is: $theNumber")

                            }
                            i++
                        }
                    }
                }
                return@OnTouchListener true
            })

            hotspotImageViewE.setImageBitmap(bitmap)
        }
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

        val detailsIntent = Intent(this, searchPage::class.java)
        //val transitionManager = contentTransitionManager
        //window.enterTransition = Explode()
        navigateUpTo(detailsIntent)
    }

    fun toHotspotDetails(hotspotID: String) {
        val intent = Intent(this, ViewHotspotDetails::class.java)
        intent.putExtra("hotspotID", hotspotID)
        setExterior()
        startActivity(intent)
    }
}