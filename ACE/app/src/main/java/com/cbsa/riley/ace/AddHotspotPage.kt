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
import android.widget.Button
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.addhotspot.*

class AddHotspotPage: AppCompatActivity(){

    var xLoc: Int = 0
    var yLoc: Int = 0
    var exterior = true
    var carImageId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addhotspot)
        exterior = intent.getBooleanExtra("exterior",true)
        carImageId = intent.getIntExtra("carImageId", 0)

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

            val imageURI:String = intent.getStringExtra("imageURI")
            Picasso.get().load(imageURI).into(addHotspotImageView)
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
        if (!exteriorHotspotArray.isEmpty()) {
            sortLocations(exteriorHotspotArray)
            val bitmap: Bitmap = Bitmap.createBitmap(previousHotspotImage.width, previousHotspotImage.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            var index = 0
            while (index <= newArrayX.size-1) {
                val xLoc = newArrayX[index]
                val yLoc = newArrayY[index]
                val left = xLoc - 20.0f
                val top = yLoc + 20.0f
                val right = xLoc + 20.0f
                val bottom = yLoc - 20.0f
                val paint = Paint()
                paint.color = Color.RED
                canvas.drawOval(left, top, right, bottom, paint)

                previousHotspotImage.setImageBitmap(bitmap)
                println("setHotspotExterior")
                index += 1
            }
        }
    }

    fun setHotspotsInterior() {
        if (!interiorHotspotArray.isEmpty()){
            sortLocations(interiorHotspotArray)
            val bitmap: Bitmap = Bitmap.createBitmap(addHotspotLayout.width, addHotspotLayout.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            var index = 0
            while (index <= newArrayX.size-1) {
                val xLoc = newArrayX[index]
                val yLoc = newArrayY[index]
                val left = xLoc - 20.0f
                val top = yLoc + 20.0f
                val right = xLoc + 20.0f
                val bottom = yLoc - 20.0f
                val paint = Paint()
                paint.color = Color.RED
                canvas.drawOval(left, top, right, bottom, paint)

                previousHotspotImage.setImageBitmap(bitmap)
                println("setHotspotInterior")
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

    fun drawHotspot(xLoc: Int, yLoc: Int){
        val bitmap: Bitmap = Bitmap.createBitmap(addHotspotImageView.width, addHotspotImageView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas (bitmap)

        val left = xLoc - 20.0f
        val top = yLoc + 20.0f
        val right = xLoc + 20.0f
        val bottom = yLoc - 20.0f
        val paint = Paint()
        paint.color = Color.CYAN
        canvas.drawOval(left, top, right, bottom, paint)
        hotspotImage.setImageBitmap(bitmap)

        val colorValue = ContextCompat.getColor(this, android.R.color.white)
        nextBttn.setTextColor(colorValue)
        nextBttn.isEnabled = true
    }

    fun nextBttnClick() {
        val nextBttn: Button = nextBttn
        nextBttn.setOnClickListener {

            if (exterior){
                exteriorHotspotArray.add(xLoc)
                exteriorHotspotArray.add(yLoc)
            } else {
                interiorHotspotArray.add(xLoc)
                interiorHotspotArray.add(yLoc)
            }

            val intent = Intent(this, HotspotDetails::class.java)
            intent.putExtra("carImageId", carImageId)
            startActivity(intent)
        }
    }
}