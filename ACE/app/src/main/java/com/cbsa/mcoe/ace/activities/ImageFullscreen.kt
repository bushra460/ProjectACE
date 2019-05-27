package com.cbsa.mcoe.ace.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cbsa.mcoe.ace.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.imagefullscreen.*

class ImageFullscreen: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imagefullscreen)

        loadPicture()
    }

    private fun loadPicture() {
        val uri = intent.getStringExtra("URI")
        Picasso.get().load(uri).into(full_photo_view)
        full_photo_view.setOnOutsidePhotoTapListener {
            onBackPressed()
        }
    }
}