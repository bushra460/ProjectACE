package com.cbsa.mcoe.ace.unused_activites

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.cbsa.mcoe.ace.R
import com.cbsa.mcoe.ace.activities.SearchPage
import kotlinx.android.synthetic.main.login.*

class LoginPage : AppCompatActivity() {
    val SHAREDPREFS = "com.cbsa.riley.ace"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val language = getSharedPreferences(SHAREDPREFS, 0).getString("language","english")

        if (language == "french"){
            loginBttn.text = "S'identifier"
            passwordText.hint = "Mot de passe"
            emailText.hint = "Courriel"
        }

        //HANDLE SEARCH BUTTON CLICKS
        val loginBttn:Button = loginBttn
        loginBttn.setOnClickListener {
            val intent = Intent(this, SearchPage::class.java)

            startActivity(intent)
        }
        //********************************

    }
}