package com.cbsa.mcoe.ace.activities

import android.app.Activity
import android.os.Bundle
import com.cbsa.mcoe.ace.R

class SettingsPage : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
    }
}