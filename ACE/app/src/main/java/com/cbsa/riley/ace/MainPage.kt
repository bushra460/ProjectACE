package com.cbsa.riley.ace

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.main.view.*

class MainActivity : Activity() {

    //VARIABLES



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)


        //SET SHARED PREFERENCES FOR ONBOARDED BOOL
        prefs = this.getSharedPreferences(SHAREDPREFS, 0)
        var isOnboarded = prefs!!.getBoolean(ONBOARDED, false)




        //HANDLE SEARCH BUTTON CLICKS
        val searchBttn = SearchBttnE
        searchBttn.setOnClickListener {
            val intent = Intent(this, searchPage::class.java)
            //intent.putExtra("keyIdentifier", value)
            startActivity(intent)


        }
    }
}
