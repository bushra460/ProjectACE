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
import kotlinx.android.synthetic.main.imageview.*

class AddHotspotPage: AppCompatActivity(){

    var exterior = true
    var carImageId = 0
    var hotspotArrayList = ArrayList<NewDataClassHotspot>()
    var imageArrayList = ArrayList<NewDataClassCarImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addhotspot)
        exterior = intent.getBooleanExtra("exterior",true)
        hotspotArrayList = intent.getExtra

        carImageId = imageArrayList[0].carImageId

        val carMake = selectedCar.make
        val carModel = selectedCar.model
        val carYear = selectedCar.year

        toolbar.title = "$carMake $carModel $carYear"

        nextBttnClick()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            if (exterior) {
                setHotspotsExterior()
            } else {
                setHotspotsInterior()
            }

            val imageURI:String =
            Picasso.get().load().into(addHotspotImageView)
            addHotspotImageView.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            xLoc = motionEvent.getX().toInt()
                            yLoc = motionEvent.getY().toInt()
                            println("X coord is: $xLoc and Y coord is: $yLoc")
                            drawHotspot(xLoc, yLoc)
                        }
                    }
                }
                return@OnTouchListener true
            })
        }
    }

    fun setHotspotsExterior(){
        hotspotArrayList.forEach {
            val bitmap: Bitmap = Bitmap.createBitmap(previousHotspotImage.width, previousHotspotImage.height, Bitmap.Config.ARGB_8888)
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

    fun setHotspotsInterior() {
        hotspotArrayList.forEach {
            val bitmap: Bitmap = Bitmap.createBitmap(previousHotspotImage.width, previousHotspotImage.height, Bitmap.Config.ARGB_8888)
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
            startActivity(intent)
        }
    }
}