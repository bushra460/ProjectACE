package com.cbsa.riley.ace

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.language.*
import kotlinx.android.synthetic.main.main.*

class LanguagePage : Activity() {

    //VARIABLES
    val SHAREDPREFS = "com.cbsa.riley.ace"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.language)

        //SET ONBOARDED TO TRUE
        var sharedPreference =  getSharedPreferences("SHAREDPREFS", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("onboarded", true)
        editor.commit()


        //HANDLE ENGLISH BUTTON CLICKS
        val englishBttn = EnglishBttnE
        englishBttn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("keyIdentifier", value)
            startActivity(intent)
        }

        //HANDLE PRIVACY BUTTON CLICKS
        val privacyBttnEN = PrivacyBttnENE
        privacyBttnEN.setOnClickListener {
            val intent = Intent(this, PrivacyPolicyEN::class.java)
            //intent.putExtra("keyIdentifier", value)
            startActivity(intent)
        }

        //HANDLE PRIVACY FRENCH BUTTON CLICKS
        val privacyBttnFR = PrivacyBttnFRE
        privacyBttnFR.setOnClickListener {
            val intent = Intent(this, PrivacyPolicyFR::class.java)
            //intent.putExtra("keyIdentifier", value)
            startActivity(intent)
        }
    }
}