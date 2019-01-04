package com.cbsa.riley.ace

import android.content.SharedPreferences

class Prefs (context: Context) {
    //Variables
    val SHAREDPREFS = "com.cbsa.riley.ace"
    val ONBOARDED = false
    var prefs: SharedPreferences = context.getSharedPreferences(SHAREDPREFS, 0)

    
}