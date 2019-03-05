package com.cbsa.riley.ace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.imagefullscreen.*

class ImageFullscreen: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imagefullscreen)
        val uri = intent.getStringExtra("URI")
        println(uri)
        Picasso.get().load(uri).into(full_photo_view)
        full_photo_view.setOnOutsidePhotoTapListener {
            onBackPressed()
        }
    }
}