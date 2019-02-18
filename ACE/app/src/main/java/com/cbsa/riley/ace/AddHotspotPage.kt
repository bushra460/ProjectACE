package com.cbsa.riley.ace

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.addhotspot.*

class AddHotspotPage: AppCompatActivity(){

    var exterior = true
    var carImageId = 0
    var hotspotArrayList = selectedCar.hotspotArrayList!!
    var imageArrayList = selectedCar.imageArrayList!![0]
    var xLoc = 0
    var yLoc = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addhotspot)
        exterior = intent.getBooleanExtra("exterior",true)

        carImageId = imageArrayList.carImageId

        val carMake = selectedCar.make
        val carModel = selectedCar.model
        val carYear = selectedCar.year

        toolbar.title = "$carMake $carModel $carYear"

        nextBttnClick()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            setHotspots()

            val imageURI:String = imageArrayList.carImageURI
            Picasso.get().load(imageURI).into(addHotspotImageView)
            addHotspotImageView.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        if(motionEvent.action == MotionEvent.ACTION_DOWN) {
                            xLoc = motionEvent.x.toInt()
                            yLoc = motionEvent.y.toInt()
                            println("X coord is: $xLoc and Y coord is: $yLoc")
                            drawHotspot(xLoc, yLoc)
                        }
                    }
                }
                return@OnTouchListener true
            })
        }
    }

    fun setHotspots() {
        val bitmap: Bitmap =
            Bitmap.createBitmap(previousHotspotImage.width, previousHotspotImage.height, Bitmap.Config.ARGB_8888)
        hotspotArrayList.forEach {
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

                    previousHotspotImage.setImageBitmap(bitmap)
                    println("setHotspotExterior")
                }
            }
        }
    }

    fun drawHotspot(xLoc: Int, yLoc: Int){
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

        val colorValue = ContextCompat.getColor(this, android.R.color.white)
        nextBttn.setTextColor(colorValue)
        nextBttn.isEnabled = true
    }

    fun nextBttnClick() {
        nextBttn.setOnClickListener {
            val intent = Intent(this, HotspotDetails::class.java)
            intent.putExtra("xLoc", xLoc)
            intent.putExtra("yLoc", yLoc)
            startActivity(intent)
        }
    }
}