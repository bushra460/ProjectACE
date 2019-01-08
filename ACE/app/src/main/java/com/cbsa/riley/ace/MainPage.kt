package com.cbsa.riley.ace

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.content.Context
import com.cbsa.riley.ace.R.layout.settings
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
        var checkBool = sharedPreference.getBoolean("onboarded", false)
        //*****************************************

        //SET TO FALSE FOR WORKING ON LANGUAGE PAGE
        checkBool = true
        //****************************************


        //Check if Onboarding has happened and navigate to onboarding if it hasn't
        if (!checkBool){
            val intent = Intent(this, LanguagePage::class.java)
            //intent.putExtra("keyIdentifier", value)
            startActivity(intent)
        } else {
            println("not onboarded")
        }

        if(sharedPreference.getBoolean("onboarded", false) == null) {
            var editor = sharedPreference.edit()
            editor.putBoolean("onboarded", false)
            editor.commit()
        }
        //********************************************


        //HANDLE SEARCH BUTTON CLICKS
        val searchBttn = SearchBttnE
        searchBttn.setOnClickListener {
            val intent = Intent(this, searchPage::class.java)
            //intent.putExtra("keyIdentifier", value)
            startActivity(intent)
        }
        //********************************


        //HANDLE SETTINGS BUTTON CLICKS
        val settingsBttn = SettingsBttnE
        settingsBttn.setOnClickListener {
            val intent = Intent(this, SettingsPage::class.java)
            //intent.putExtra("keyIdentifier", value)
            startActivity(intent)
        }
        //********************************
    }
}
