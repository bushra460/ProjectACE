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

    var hotspotArray: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addhotspot)
        var xCoord: Int
        var yCoord: Int
        val imageURI:String = intent.getStringExtra("imageURI")
        Picasso.get().load(imageURI).into(addHotspotImageView)
        addHotspotImageView.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        xCoord = motionEvent.getX().toInt()
                        yCoord = motionEvent.getY().toInt()
                        println("X coord is: $xCoord and Y coord is: $yCoord")
                        drawHotspot(xCoord, yCoord)
                    }
                }
            }
            return@OnTouchListener true
        })
        nextBttnClick()
    }

    fun drawHotspot(xCoord: Int, yCoord: Int){
        val bitmap: Bitmap = Bitmap.createBitmap(addHotspotImageView.width, addHotspotImageView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas (bitmap)

        val left = xCoord - 50.0f
        val top = yCoord + 50.0f
        val right = xCoord + 50.0f
        val bottom = yCoord - 50.0f
        val paint = Paint(Color.parseColor("#9b59b6"))
        canvas.drawOval(left, top, right, bottom, paint)
        hotspotImage.setImageBitmap(bitmap)

        hotspotArray.add(xCoord)
        hotspotArray.add(yCoord)

        val colorValue = ContextCompat.getColor(this, android.R.color.white)
        nextBttn.setTextColor(colorValue)
        nextBttn.isEnabled = true
    }

    fun nextBttnClick() {
        val nextBttn: Button = nextBttn
        nextBttn.setOnClickListener {
            val intent = Intent(this, SettingsPage::class.java)
            intent.putExtra("hotspotArray", hotspotArray)
            startActivity(intent)
        }
    }
}