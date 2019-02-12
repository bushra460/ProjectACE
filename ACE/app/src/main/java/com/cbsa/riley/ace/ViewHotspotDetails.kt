package com.cbsa.riley.ace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.viewhotspotdetails.*

class ViewHotspotDetails: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewhotspotdetails)
        val hotspotID = intent.getStringExtra("hotspotID")
        textView.text = hotspotID
    }
}