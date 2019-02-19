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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.imageview.*

var exterior = true
val SHAREDPREFS = "com.cbsa.riley.ace"
var selectedCar = carArray[0]
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
            var numTab = 0
            var index = 0
            val carId = intent.getIntExtra("carId", 0)
            carArray.forEach {
                if (carId == it.carId) {
                    selectedCar = carArray[index]
                    println(carId.toString() + "  " + it.carId.toString())
                    println("Index:  $index")
                }
                index += 1
            }

            println("selected car:   $selectedCar")


            hotspotArrayList = selectedCar.hotspotArrayList!!

            println("hotspot array list:   " + hotspotArrayList)
            println(selectedCar.hotspotArrayList)

            imageArrayList = selectedCar.imageArrayList!!


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

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }
            })
        }
    }

    fun setExteriorImage() {
        setExterior()
        imageArrayList.forEach {
            if (it.carId == selectedCar.carId) {
                if (it.exteriorImage) {
                    println("Exterior view")

                    imageViewE.setImageResource(0)
                    hotspotImageViewE.setImageResource(0)
                    Picasso.get().load(it.carImageURI).into(imageViewE)
                    setHotspots()
                }
            }
        }
    }

    fun setInteriorImage() {
        setExterior()
        imageArrayList.forEach {
            if (it.carId == selectedCar.carId) {
                if (!it.exteriorImage) {
                    println("Exterior view")

                    imageViewE.setImageResource(0)
                    hotspotImageViewE.setImageResource(0)
                    Picasso.get().load(it.carImageURI).into(imageViewE)
                    setHotspots()
                } else {
                    imageViewE.setImageResource(0)
                    hotspotImageViewE.setImageResource(0)
                    Picasso.get().load("https://via.placeholder.com/150").into(imageViewE)
                }
            }
        }
    }

    fun setHotspots() {
        val bitmap: Bitmap = Bitmap.createBitmap(hotspotImageViewE.width, hotspotImageViewE.height, Bitmap.Config.ARGB_8888)
        hotspotArrayList.forEach {
            println("alskdf;alsdjf;akjsd;fkasdfj;asdkjf;laskjd;fkja;sdlkj;aslkdfj;")
            if (it.carId == selectedCar.carId) {
                if (it.exteriorImage == exterior) {
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

                    hotspotImageViewE.setImageBitmap(bitmap)

                    hotspotImageViewE.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
                        when (motionEvent.action) {
                            MotionEvent.ACTION_DOWN -> {

                                val x: Int = motionEvent.x.toInt()
                                val y: Int = motionEvent.y.toInt()
                                val bitmapWidth = 30
                                val bitmapHeight = 30
                                var i = 0

                                println("X location Tapped: " + motionEvent.x.toInt())
                                println("Y location Tapped: " + motionEvent.y.toInt())

                                while (i < hotspotArrayList.size) {
                                    val xLocCheck = hotspotArrayList[i].xLoc
                                    val yLocCheck = hotspotArrayList[i].yLoc

                                    if (x > xLocCheck - bitmapWidth && x < xLocCheck + bitmapWidth && y > yLocCheck - bitmapHeight && y < yLocCheck + bitmapHeight) {
                                        val xdistance = Math.abs(hotspotArrayList[0].xLoc - x)
                                        val ydistance = Math.abs(hotspotArrayList[0].yLoc - y)
                                        var distance = xdistance + ydistance
                                        var idx = 0
                                        for (c in 1 until hotspotArrayList.size) {
                                            val cxdistance = Math.abs(hotspotArrayList[c].xLoc - x)
                                            val cydistance = Math.abs(hotspotArrayList[c].yLoc - y)
                                            val cdistance = cxdistance + cydistance
                                            if (cdistance < distance) {
                                                idx = c
                                                distance = cdistance
                                            }
                                        }
                                        val theNumber = hotspotArrayList[idx].hotspotId!!
                                        //println("hotspot ID of chosen Hotspot: " + exteriorHotspotID[idx])
                                        //println("exteriorHotspotID array: $exteriorHotspotID")
                                        toHotspotDetails(theNumber)
                                        println("the number is: $theNumber")
                                    }

                                    i++
                                }
                            }
                        }
                        return@OnTouchListener true
                    })
                }
            }
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
        carArray.clear()
    }

    fun toHotspotDetails(hotspotID: Int) {
        val intent = Intent(this, ViewHotspotDetails::class.java)
        intent.putExtra("hotspotID", hotspotID)
        setExterior()
        startActivity(intent)
    }
}