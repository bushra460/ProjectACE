package com.cbsa.riley.ace

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.imageview.*


var basicCarA: ArrayList<basicCar> = ArrayList()
var exteriorImageURIArray: ArrayList<String> = ArrayList()
var interiorImageURIArray: ArrayList<String> = ArrayList()
var exteriorHotspotArray: ArrayList<Int> = ArrayList()
var interiorHotspotArray: ArrayList<Int> = ArrayList()
var exteriorHotspotID: ArrayList<Any> = ArrayList()
var interiorHotspotID: ArrayList<Any> = ArrayList()
val newArrayX = ArrayList<Int>()
val newArrayY = ArrayList<Int>()
var exterior = true
val SHAREDPREFS = "com.cbsa.riley.ace"

class ImageViewPage: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageview)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            checkExterior()
            addCar()

            var numTab: Int = 0
            val carMake = basicCarA[0].make
            val carModel = basicCarA[0].model
            val carYear = basicCarA[0].year

            imageViewToolbar.title = "$carMake $carModel $carYear"
            println("$carMake $carModel $carYear")

            fab.setOnClickListener {
                val intent = Intent(this, AddHotspotPage::class.java)
                if(numTab == 0) {
                    intent.putExtra("imageURI", exteriorImageURIArray[0])
                    intent.putExtra("exterior", true)
                    setExterior()
                    startActivity(intent)

                } else {
                    intent.putExtra("imageURI", "https://via.placeholder.com/150")
                    //intent.putExtra("imageURI", interiorImageURIArray[0])
                    intent.putExtra("exterior", false)
                    setExterior()
                    startActivity(intent)
                }
            }

//        //HANDLE LISTVIEW BUTTON CLICKS
//        val listViewBttn: Button = listViewBttn
//        listViewBttn.setOnClickListener {
//
//            val intent = Intent(this, SettingsPage::class.java)
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

    fun setExteriorImage(){
        println("Option 1")
        imageViewE.setImageResource(0)
        hotspotImageViewE.setImageResource(0)
        Picasso.get().load(exteriorImageURIArray[0]).into(imageViewE)
        setHotspotsExterior()
    }

    fun setInteriorImage(){
        println("Option 2")
        imageViewE.setImageResource(0)
        hotspotImageViewE.setImageResource(0)
        //Picasso.get().load(interiorImageURIArray[0]).into(imageViewE)
        Picasso.get().load("https://via.placeholder.com/150").into(imageViewE)
        setHotspotsInterior()
    }

    fun getURI(){
        carArray.forEach{
            if (it.make == basicCarA[0].make && it.model == basicCarA[0].model && it.year == basicCarA[0].year){
                val carImageURI = Uri.parse(it.carImageURI).toString()
                val dataRemoved =  carImageURI.replace("\"","")
                val manufacturerId = it.carImageId
                val exteriorImage = it.exteriorImage
                if (exteriorImage){
                    exteriorImageURIArray.add(dataRemoved)
                    println("Image Added to Exterior Array")
                } else {
                    interiorImageURIArray.add(dataRemoved)
                    println("Image Added to Interior Array")
                }
                println(carImageURI)
                if (exteriorHotspotArray.isEmpty()) {
                    hotspotArray.forEach {
                        if (it.carImageId == manufacturerId) {
                            if (exteriorImage) {
                                val xLoc = it.xLoc
                                val yLoc = it.yLoc

                                println("EXTERIOR HOTSPOT xLoc: $xLoc yLoc: $yLoc")

                                exteriorHotspotID.add(it.hotspotId)
                                exteriorHotspotArray.add(xLoc)
                                exteriorHotspotArray.add(yLoc)
                            } else {
                                val xLoc = it.xLoc
                                val yLoc = it.yLoc

                                println("INTERIOR HOTSPOT xLoc: $xLoc yLoc: $yLoc")

                                interiorHotspotID.add(it.hotspotId)
                                interiorHotspotArray.add(xLoc)
                                interiorHotspotArray.add(yLoc)
                            }
                        }
                    }
                }
            }
        }
        if (exterior) {
            setExteriorImage()
        } else {
            setInteriorImage()
        }
    }

    fun setHotspotsExterior(){
        if (!exteriorHotspotArray.isEmpty()) {
            sortLocations(exteriorHotspotArray)

            val bitmap: Bitmap = Bitmap.createBitmap(hotspotImageViewE.width, hotspotImageViewE.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            var index = 0
            while (index <= newArrayX.size-1) {
                val xLoc = newArrayX[index]
                val yLoc = newArrayY[index]
                val left = xLoc - 20.0f
                val top = yLoc + 20.0f
                val right = xLoc + 20.0f
                val bottom = yLoc - 20.0f
                val color = Color.parseColor("#ffffff")
                val paint = Paint()
                paint.color = Color.RED
                //canvas.drawPaint(paint)
                canvas.drawOval(left, top, right, bottom, paint)

                hotspotImageViewE.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {

                                val x:Int = motionEvent.x.toInt()
                                val y:Int = motionEvent.y.toInt()
                                val bitmapWidth = 20
                                val bitmapHeight = 20

                                println(motionEvent.x.toInt())
                                println(motionEvent.y.toInt())

                                if (x > xLoc - bitmapWidth && x < xLoc + bitmapWidth && y > yLoc - bitmapHeight && y < yLoc + bitmapHeight) {

                                    var distance = Math.abs(newArrayX[0] - x)
                                    var idx = 0
                                    for (c in 1 until newArrayX.size) {
                                        val cdistance = Math.abs(newArrayX[c] - x)
                                        if (cdistance < distance) {
                                            idx = c
                                            distance = cdistance
                                        }
                                    }
                                    val theNumber = newArrayX[idx]
                                    println(exteriorHotspotID[idx])


                                    println("the number is: $theNumber")
                                }
                        }
                    }
                    return@OnTouchListener true
                })

                hotspotImageViewE.setImageBitmap(bitmap)
                index += 1
            }
        }
    }

    fun setHotspotsInterior() {
        if (!interiorHotspotArray.isEmpty()){
            sortLocations(interiorHotspotArray)
            val bitmap: Bitmap = Bitmap.createBitmap(1080, 1584, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            var index = 0
            while (index <= newArrayX.size-1) {
                val xLoc = newArrayX[index]
                val yLoc = newArrayY[index]
                val left = xLoc - 20.0f
                val top = yLoc + 20.0f
                val right = xLoc + 20.0f
                val bottom = yLoc - 20.0f
                val paint = Paint(Color.parseColor("#9b59b6"))
                canvas.drawOval(left, top, right, bottom, paint)

                hotspotImageViewE.setImageBitmap(bitmap)
                index += 1
            }
        }
    }

    fun sortLocations(arrayList: ArrayList<Int>){
        val length = arrayList.size
        var index = 0
        newArrayX.clear()
        newArrayY.clear()
        while (index <= length-1){
            newArrayX.add(arrayList[index])
            index += 2
            newArrayY.add(arrayList[index-1])
        }
        println("X Array: $newArrayX    Y Array: $newArrayY")
    }

    fun addCar() {
        val carMakeIntent:String = intent.getStringExtra("carMake")
        val carModelIntent:String = intent.getStringExtra("carModel")
        val carYearIntent:String = intent.getStringExtra("carYear")
        basicCarA.clear()
        basicCarA.add(basicCar(carMakeIntent, carModelIntent, carYearIntent))
        getURI()
    }

    fun checkExterior(){
        val prefs = getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE)
        exterior = prefs.getBoolean("exterior", true)
    }

    fun setExterior(){
        exterior = tabLayout.selectedTabPosition == 0
        getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE).edit().putBoolean("exterior", exterior).apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        basicCarA.clear()
        exteriorImageURIArray.clear()
        interiorImageURIArray.clear()
        exteriorHotspotArray.clear()
        interiorHotspotArray.clear()
        exteriorHotspotID.clear()
        interiorHotspotID.clear()
        newArrayX.clear()
        newArrayY.clear()

        val detailsIntent = Intent(this, searchPage::class.java)
        navigateUpTo(detailsIntent)
    }
}