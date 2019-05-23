package com.cbsa.mcoe.ace.activities

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
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.cbsa.mcoe.ace.R
import com.cbsa.mcoe.ace.data_classes.HotspotDeets
import com.cbsa.mcoe.ace.data_classes.NewDataClassCar
import com.cbsa.mcoe.ace.data_classes.NewDataClassCarImage
import com.cbsa.mcoe.ace.data_classes.NewDataClassHotspot
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.imageview.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

var exterior = true
const val SHAREDPREFS = "com.cbsa.riley.ace"
const val url = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/cars/"
var testCar = carArray[0]
lateinit var selectedCar: NewDataClassCar
var hotspotArrayList = ArrayList<NewDataClassHotspot>()
var imageArrayList = ArrayList<NewDataClassCarImage>()
var selectedImage = 0
var carValue = ""
var imageNumberX = 2
var imageNumber = 2

class ImageViewPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageview)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            clearArrays()
            checkCar()
            checkExterior()
            setTitle()
        }
    }

    //Clears imageArraylist and Hotspotarraylist
    private fun clearArrays() {
        hotspotArrayList.clear()
        imageArrayList.clear()
    }

    //Sets car to selectedCar Variable
    private fun checkCar() {
         var index = 0
         val carId = intent.getIntExtra("carId", 0)
        println("RILEY___----" + carId)
         carArray.forEach {
             val car = it
             if (carId == car.carId) {
                 testCar = carArray[index]
             }
             index += 1
         }
         getDetails()
     }

    //grabs car specific images and hotspots from the web and calls setTab(), setOnClickListeners()
    private fun getDetails() {
         fun workload(data: String) {
             val gson = Gson()
             val parse = JsonParser().parse(data)

             val carData = JsonParser().parse((gson.toJson(parse))).asJsonObject

             val carImageArray = carData.get("carImage").asJsonArray
                carImageArray.forEach { CarImageObjJson ->
                    val carImageObj = CarImageObjJson.asJsonObject
                    val carImageId = carImageObj.get("carImageId").asInt
                    val carImageURI = carImageObj.get("uri").asString
                    val exteriorImage = carImageObj.get("exteriorImage").asBoolean
                    val displayPic = carImageObj.get("active").asBoolean

                    val image = NewDataClassCarImage(carImageId, carImageURI, exteriorImage, testCar.carId, displayPic)
                    imageArrayList.add(image)

                    val hotspotArrayValue = carImageObj.get("hotspotLocations").asJsonArray
                    hotspotArrayValue.forEach { hotspotObjJson ->
                        val hotspotObj = hotspotObjJson.asJsonObject
                        val xLoc = hotspotObj.get("xLoc").asInt
                        val yLoc = hotspotObj.get("yLoc").asInt
                        val hotspotId = hotspotObj.get("hotspotId").asInt
                        val hotspotDesc = hotspotObj.get("hotspotDesc").asString

                        val hotspotDetailsArrayObj = hotspotObj.get("hotspotDetails").asJsonArray

                        hotspotDetailsArrayObj.forEach {
                            val hotspotDetailsObj = it.asJsonObject
                            val hotspotUri = hotspotDetailsObj.get("uri").asString
                            val hotspotNotes = hotspotDetailsObj.get("notes").asString
                            val hotspotDetailsActive = hotspotDetailsObj.get("active").asBoolean

                            val hotspotDetailsArray = ArrayList<HotspotDeets>()
                            val details = HotspotDeets(
                                hotspotUri,
                                hotspotNotes,
                                hotspotDetailsActive
                            )
                            hotspotDetailsArray.add(details)
                            val hotspot = NewDataClassHotspot(hotspotId, xLoc, yLoc, hotspotDesc, true, carImageId, hotspotDetailsArray, testCar.carId, exteriorImage)
                            hotspotArrayList.add(hotspot)
                            selectedCar = NewDataClassCar(testCar.carId, testCar.active, testCar.makeId, testCar.make, testCar.modelId, testCar.model, testCar.yearId, testCar.year, imageArrayList, hotspotArrayList)
                            println(selectedCar.hotspotArrayList.toString())
                        }
                    }
                }
             runOnUiThread{
                 progress_loader.visibility = View.INVISIBLE
                 setTab()
                 setClickListeners()
             }
             println("raw parsed data: $parse")
         }

         GlobalScope.launch {
             try {
                 val urlCarId = "$url${testCar.carId}"
                 URL(urlCarId).run {
                     openConnection().run {
                         val httpURLConnection = this as HttpURLConnection

                         httpURLConnection.requestMethod = "GET"
                         httpURLConnection.setRequestProperty("charset", "utf-8")
                         httpURLConnection.setRequestProperty("Content-Type", "application/json")

                         if (httpURLConnection.responseCode != 200) {
                             Toast.makeText(this@ImageViewPage, "Server Error, Please restart application", Toast.LENGTH_SHORT).show()
                         }

                         println("server response code " + httpURLConnection.responseCode)
                         workload(inputStream.bufferedReader().readText())
                     }
                 }

             } catch (e: Error){
                 Toast.makeText(this@ImageViewPage, e.toString(), Toast.LENGTH_SHORT).show()
             }
         }
    }

    //Sets the toolbar title on load
    private fun setTitle() {
         val carMake = testCar.make
         val carModel = testCar.model
         val carYear = testCar.year
         carValue = "$carMake $carModel $carYear"
         imageViewToolbar.title = carValue
     }

    private fun setClickListeners() {
         var numTab = 0
         val picsArray = ArrayList<Int>()
         imageArrayList.forEach {
             if (it.carId == testCar.carId){
                 if (!it.exteriorImage){
                     picsArray.add(it.carId)
                 }
             }
         }
         val picsArrayX = ArrayList<Int>()
         imageArrayList.forEach {
             if (it.carId == testCar.carId){
                 if (it.exteriorImage) {
                     picsArrayX.add(it.carId)
                 }
             }
         }

         if (exterior) pictureCount.text = "1 of " + picsArrayX.size else if (!exterior) pictureCount.text = "1 of " + picsArray.size

         rotateFAB.setOnClickListener {
             if (exterior) {
                 setExteriorImage(selectedImage + 1)
                 changeExteriorPictureCounter(picsArrayX)
             } else if (!exterior) {
                 setInteriorImage(selectedImage + 1)
                 changeInteriorPictureCounter(picsArray)
             }
         }
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
         listViewBttn.setOnClickListener {
             val intent = Intent(this, ListViewPage::class.java)
             startActivity(intent)
         }
         tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
             @SuppressLint("SetTextI18n")
             override fun onTabSelected(tab: TabLayout.Tab) {
                 numTab = tab.position
                 when (numTab) {
                     0 -> {setExteriorImage(selectedImage)
                         imageNumberX = 2
                         pictureCount.text = "1 of ${picsArrayX.size}"
                     }
                     else -> {
                         setInteriorImage(selectedImage)
                         imageNumber = 2
                         pictureCount.text = "1 of ${picsArray.size}"
                     }
                 }
             }
             override fun onTabUnselected(tab: TabLayout.Tab) {}
             override fun onTabReselected(tab: TabLayout.Tab) {}
         })
     }

    private fun setTab() {
         if (exterior) {
             setExteriorImage(selectedImage)
         } else {
             setInteriorImage(selectedImage)
             val tab = tabLayout.getTabAt(1)
             tab?.select()
         }
     }

    private fun changeExteriorPictureCounter(picsArray: ArrayList<Int>){
         if (imageNumberX == picsArray.size+1) {
             imageNumberX = 1
         }
         val arraySize = picsArray.size
         val count = "$imageNumberX of $arraySize"
         pictureCount.text = count
         imageNumberX++
     }

    private fun changeInteriorPictureCounter(picsArray: ArrayList<Int>){
         if (imageNumber == picsArray.size+1) {
             imageNumber = 1
         }
         val arraySize = picsArray.size
         val count = "$imageNumber of $arraySize"
         pictureCount.text = count
         imageNumber++
     }

    /*
     Takes current imageIndex and checks if it's at the end of the array of images
     if the imageIndex is at the end then it resets the index
     Sets the next image to the imageViewE
      */
    fun setExteriorImage(imageIndex: Int) {
        setExterior()
        var index = imageIndex
        var imageSet = false
        do {
            if (index == imageArrayList.size){
                index = 0
            }
            if (imageArrayList[index].carId == testCar.carId) {
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

    /*
     Takes current imageIndex and checks if it's at the end of the array of images
     if the imageIndex is at the end then it resets the index
     Sets the next image to the imageViewE
      */
    fun setInteriorImage(imageIndex: Int) {
        setExterior()
        var index = imageIndex
        var imageSet = false
        do {
            if (index == imageArrayList.size){
                index = 0
            }
            if (imageArrayList[index].carId == testCar.carId) {
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

    private fun setHotspots() {
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

    //Checks SHAREDPREFS Exterior value and sets it to global variable
    private fun checkExterior() {
        val prefs = getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE)
        exterior = prefs.getBoolean("exterior", true)
    }

    //sets SHAREDPREFS value Exterior to whatever is currently selected
    private fun setExterior() {
        exterior = tabLayout.selectedTabPosition == 0
        getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE).edit().putBoolean("exterior", exterior).apply()
    }

    //overrides normal back pressed function and takes you back to the searchScreen instead of through the navigation stack
    //TODO: fix animation, or just fix navigation stack
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

    //calls setExterior() and starts hotspotDetails activity and passes the hotspot id
    private fun toHotspotDetails(hotspotID: Int) {
        setExterior()
        val intent = Intent(this, ViewHotspotDetails::class.java)
        intent.putExtra("hotspotID", hotspotID)
        startActivity(intent)
    }
}
