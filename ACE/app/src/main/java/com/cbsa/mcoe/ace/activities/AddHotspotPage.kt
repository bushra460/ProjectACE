package com.cbsa.mcoe.ace.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.cbsa.mcoe.ace.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.addhotspot.*

class AddHotspotPage: AppCompatActivity(){

    private var exterior = true
    private var carImageId = 0
    private val hotspotArrayList = selectedCar.hotspotArrayList
    private val imageArrayList = selectedCar.imageArrayList
    private var xLoc = 0
    private var yLoc = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addhotspot)
        val prefs = getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE)
        exterior = prefs.getBoolean("exterior", true)
        val carMake = selectedCar.make
        val carModel = selectedCar.model
        val carYear = selectedCar.year
        toolbar.title = "$carMake $carModel $carYear"
    }

    //calls functions to load images and hotspots onto the page
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            setImage()
            nextBttnClick()
            setHotspots()
            //onClick of hotspot image view it checks the motionEvent type and then calls the drawHotspot() function
            addHotspotImageView.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        if(motionEvent.action == MotionEvent.ACTION_DOWN) {
                            grabLocation(motionEvent)
                        }
                    }
                }
                return@OnTouchListener true
            })
        }
    }

    //grabs the x and y location of the touch and sends it to the drawHotspot() function
    private fun grabLocation(motionEvent: MotionEvent) {
        xLoc = motionEvent.x.toInt()
        yLoc = motionEvent.y.toInt()
        println("X coord is: $xLoc and Y coord is: $yLoc")
        drawHotspot(xLoc, yLoc)
    }

    //Creates a bitmap, iterates through hotspotArrayList, draws circles on a new Canvas and sets it to the bitmap, sets the bitmap to the imageview
    private fun setHotspots() {
        val bitmap: Bitmap = Bitmap.createBitmap(previousHotspotImage.width, previousHotspotImage.height, Bitmap.Config.ARGB_8888)
        if (hotspotArrayList != null) {
            hotspotArrayList.forEach {
                val hotspot = it
                if (imageArrayList != null) {
                    if (hotspot.carImageId == imageArrayList[selectedImage].carImageId) {
                        if (hotspot.exteriorImage == exterior) {
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

                            previousHotspotImage.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }
    }

    //Takes x and y locations and draws a circle on a canvas on a bitmap and puts it in the imageview
    private fun drawHotspot(xLoc: Int, yLoc: Int){
        val bitmap: Bitmap = Bitmap.createBitmap(addHotspotImageView.width, addHotspotImageView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas (bitmap)

        val left = xLoc - 30.0f
        val top = yLoc + 30.0f
        val right = xLoc + 30.0f
        val bottom = yLoc - 30.0f
        val paint = Paint()
        val stroke = Paint()

        paint.color = Color.CYAN
        stroke.color = Color.CYAN
        stroke.style = Paint.Style.STROKE
        stroke.strokeWidth = 10.0f

        canvas.drawOval(left + 15, top - 15, right - 15, bottom + 15, paint)
        canvas.drawOval(left, top, right, bottom, stroke)
        hotspotImage.setImageBitmap(bitmap)

        enableButtonWhite(nextBttn)
    }

    //Sends user to HotspotDetailsPage with X-Y Locations
    private fun nextBttnClick() {
        nextBttn.setOnClickListener {
            val intent = Intent(this, HotspotDetails::class.java)
            intent.putExtra("xLoc", xLoc)
            intent.putExtra("yLoc", yLoc)
            startActivity(intent)
        }
    }

    //Loads image into addHotspotImageView
    private fun setImage() {
        if (imageArrayList != null) {
            carImageId = imageArrayList[selectedImage].carImageId
            Picasso.get().load(imageArrayList[selectedImage].carImageURI).into(addHotspotImageView)
        }
    }

    //enables button
    private fun enableButtonWhite(button: Button) {
        button.isEnabled = true
        val blue = ContextCompat.getColor(this, R.color.white)
        button.setTextColor(blue)
    }

}