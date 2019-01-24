package com.cbsa.riley.ace

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import kotlinx.android.synthetic.main.imageview.*



val car: ArrayList<Car> = ArrayList()

class ImageViewPage: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageview)
        addCar()
        var carMake = car[0].make
        var carModel = car[0].model
        var carYear = car[0].year
        toolbar.title = "$carMake $carModel $carYear"
        println("$carMake $carModel $carYear")

        fab.setOnClickListener { view ->
            val intent = Intent(this, SettingsPage::class.java)
            startActivity(intent)
        }

        //HANDLE LISTVIEW BUTTON CLICKS
        val listViewBttn: Button = listViewBttn
        listViewBttn.setOnClickListener {

            val intent = Intent(this, SettingsPage::class.java)

            startActivity(intent)
        }

        val tabLayout = tabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                var numTab = tab.position
                println("The Tab selected: $numTab")
                when (numTab) {

                    else -> {

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })




    }

    // Adds cars to the empty cars ArrayList
    fun addCar() {
        var carMakeIntent:String = intent.getStringExtra("carMake")
        var carModelIntent:String = intent.getStringExtra("carModel")
        var carYearIntent:String = intent.getStringExtra("carYear")
        car.clear()
        car.add(Car(carMakeIntent, carModelIntent, carYearIntent))
    }
}