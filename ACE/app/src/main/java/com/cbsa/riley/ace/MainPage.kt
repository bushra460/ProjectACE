package com.cbsa.riley.ace

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : Activity() {

    //VARIABLES
    val SHAREDPREFS = "com.cbsa.riley.ace"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        //GET DATA FROM CLOUD
        makeData()

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

    fun makeData() {

        if (makeArray.count() >= 2) {
            makeArray.clear()
            makeArray.add("Select One")
        }

        //TAKE RESULT OF GET CALL AND PARSE THE DATA
        fun workload(data: String) {
            println(data)
            var gson = Gson()
            var manufacturerModel = gson.fromJson(data, ManufacturerModel::class.java)
            val manufacturer1 = JsonParser().parse((gson.toJson(manufacturerModel))).asJsonObject
            val manufacturer2 = manufacturer1.getAsJsonObject("_embedded")
            val manufacturer3 = manufacturer2.getAsJsonArray("manufacturers")
            var manufacturer4: JsonObject
            var manufacturer5: String
            println("data: $manufacturer1")
            manufacturer3.forEach {
                manufacturer4 = it.asJsonObject
                manufacturer5 = manufacturer4.get("name").asString
                makeArray.add(manufacturer5)
                println(makeArray)
            }
        }

        //MAKE GET CALL AND PASS TO WORKLOAD FUNCTION
        GlobalScope.launch {
            val json = URL(makeurl).readText()
            workload(json)
        }
    }

}
