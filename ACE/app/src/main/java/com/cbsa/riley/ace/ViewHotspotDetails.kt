package com.cbsa.riley.ace

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


        hotspotArrayList.forEach {
            if (hotspotID == it.hotspotId){
                uri = it.hotspotUri
                Picasso.get().load(it.hotspotUri).into(photo_view)

                textView.text = it.notes
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