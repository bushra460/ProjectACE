package com.cbsa.riley.ace

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import kotlinx.android.synthetic.main.login.*

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //HANDLE SEARCH BUTTON CLICKS
        val loginBttn:Button = loginBttn
        loginBttn.setOnClickListener {
            val intent = Intent(this, SearchPage::class.java)

            startActivity(intent)
        }
        //********************************

    }
}