package com.cbsa.riley.ace

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.main.*

class MainActivity : Activity() {

    val SHAREDPREFS = "com.cbsa.riley.ace"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        setExterior()

        //HANDLE SEARCH BUTTON CLICKS
        val searchBttn = SearchBttnE
        searchBttn.setOnClickListener {
            val intent = Intent(this, searchPage::class.java)

            startActivity(intent)
        }
        //********************************


        //HANDLE SETTINGS BUTTON CLICKS
        val settingsBttn = SettingsBttnE
        settingsBttn.setOnClickListener {
            val intent = Intent(this, SettingsPage::class.java)

            startActivity(intent)
        }
        //********************************
    }

    fun setExterior() {
        getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE).edit().putBoolean("exterior", true).apply()
    }


}
