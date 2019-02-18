package com.cbsa.riley.ace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewhotspotdetails.*

class ViewHotspotDetails: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewhotspotdetails)
        val hotspotID = intent.getIntExtra("hotspotID", 0)
        //val carValue = intent.getStringExtra("carValue")
        //println(carValue)
        //toolbar.title = carValue

        hotspotArrayList.forEach {
            if (hotspotID == it.hotspotId){
                Picasso.get().load(it.hotspotUri).into(imageView)

                textView.text = it.notes
            }
        }

    }
}