package com.cbsa.mcoe.ace

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewhotspotdetails.*

class ViewHotspotDetails: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewhotspotdetails)
        val hotspotID = intent.getIntExtra("hotspotID", 0)
        var uri = ""
        var index = 0


        hotspotArrayList.forEach {
            if (hotspotID == it.hotspotId){
                println(it.hotspotDetails.size)
                if (it.hotspotDetails.size-1 == 0){
                    uri = it.hotspotDetails[0].uri
                    Picasso.get().load(it.hotspotDetails[0].uri).into(photo_view)
                } else if (it.hotspotDetails.size-1 == 1){
                    uri = it.hotspotDetails[0].uri
                    Picasso.get().load(it.hotspotDetails[0].uri).into(photo_view)
                    uri = it.hotspotDetails[1].uri
                    Picasso.get().load(it.hotspotDetails[1].uri).into(imageViewSecondary)
                }


                textView.text = it.hotspotDetails[0].notes
                toolbar.title = it.hotspotDesc
            }
        }
        photo_view.setOnClickListener {
            val intent = Intent(this, ImageFullscreen::class.java)
            intent.putExtra("URI", uri)
            startActivity(intent)
        }
    }
}