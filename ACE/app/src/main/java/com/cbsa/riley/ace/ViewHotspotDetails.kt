package com.cbsa.riley.ace

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewhotspotdetails.*

class ViewHotspotDetails: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewhotspotdetails)
        val hotspotID = intent.getStringExtra("hotspotID")
        val carValue = intent.getStringExtra("carValue")
        println(carValue)
        toolbar.title = carValue

        hotspotArray.forEach {
            if (hotspotID.toInt() == it.hotspotId){
                val uri = Uri.parse(it.hotspotUri)
                Picasso.get().load(uri).into(imageView)

                textView.text = it.notes
            }
        }

    }
}