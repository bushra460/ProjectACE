package com.cbsa.mcoe.ace

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.main.*
import java.util.*




class MainActivity : Activity() {

    val SHAREDPREFS = "com.cbsa.riley.ace"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        setExterior()

        //HANDLE LOGIN BUTTON CLICKS
        val englishBttn = EnglishBttnE
        englishBttn.setOnClickListener {
            setLanguage("english")
            Locale.ENGLISH
            val intent = Intent(this, SearchPage::class.java)
            startActivity(intent)
        }
        //********************************

        //HANDLE LOGIN BUTTON CLICKS
        val frenchBttn = FrenchBttn
        frenchBttn.setOnClickListener {
            setLanguage("french")

            val intent = Intent(this, SearchPage::class.java)
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

    fun setLanguage(language:String) {

//        val french = Locale.FRENCH
//        val english = Locale.ENGLISH
//
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//
//        val res = this.getResources()
//        val config = Configuration(res.getConfiguration())
//        config.setLocale(french)
//        var context = this.createConfigurationContext(config)

        getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE).edit().putString("language", language).apply()
    }





}
