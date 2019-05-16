package com.cbsa.mcoe.ace.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cbsa.mcoe.ace.R
import kotlinx.android.synthetic.main.main.*
import java.util.*

class MainActivity : Activity() {

    private val sharedprefs = "com.cbsa.riley.ace"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        setExterior()
        setClickListeners()
    }

    private fun setClickListeners(){
        EnglishBttnE.setOnClickListener {
            setLanguage("english")
            Locale.ENGLISH
            val intent = Intent(this, SearchPage::class.java)
            startActivity(intent)
        }
        FrenchBttn.setOnClickListener {
            setLanguage("french")
            val intent = Intent(this, SearchPage::class.java)
            startActivity(intent)
        }
        SettingsBttnE.setOnClickListener {
            val intent = Intent(this, SettingsPage::class.java)
            startActivity(intent)
        }
    }

    private fun setExterior() { getSharedPreferences(sharedprefs, Context.MODE_PRIVATE).edit().putBoolean("exterior", true).apply() }

    private fun setLanguage(language:String) {
        getSharedPreferences(sharedprefs, Context.MODE_PRIVATE).edit().putString("language", language).apply()

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
    }





}
