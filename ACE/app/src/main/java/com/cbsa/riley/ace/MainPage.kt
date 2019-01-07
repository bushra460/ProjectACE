package com.cbsa.riley.ace

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.content.Context
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.main.view.*

class MainActivity : Activity() {

    //VARIABLES
    val SHAREDPREFS = "com.cbsa.riley.ace"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)


        //SET SHARED PREFERENCES FOR ONBOARDED BOOL

        var sharedPreference =  getSharedPreferences("SHAREDPREFS",Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("onboarded", false)
        editor.commit()

        var checkBool = sharedPreference.getBoolean("onboarded", false)
        if (checkBool){
            val intent = Intent(this, Language::class.java)
            //intent.putExtra("keyIdentifier", value)
            startActivity(intent)
        } else {
            println("not onboarded")
        }




        //HANDLE SEARCH BUTTON CLICKS
        val searchBttn = SearchBttnE
        searchBttn.setOnClickListener {
            val intent = Intent(this, searchPage::class.java)
            //intent.putExtra("keyIdentifier", value)
            startActivity(intent)


        }
    }
}
