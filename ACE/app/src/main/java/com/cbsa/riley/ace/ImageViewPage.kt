package com.cbsa.riley.ace

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import kotlinx.android.synthetic.main.imageview.*


var basicCarA: ArrayList<basicCar> = ArrayList()


class ImageViewPage: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageview)

        addCar()

        var carMake = basicCarA[0].make
        var carModel = basicCarA[0].model
        var carYear = basicCarA[0].year

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






    }

    fun setImage(carImageURI: Uri){
        imageView.setImageURI(carImageURI)
        val tabLayout = tabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                var numTab = tab.position
                when (numTab) {
                    0 -> println("Option 1")
                    0 -> imageView.setImageURI(carImageURI)
                    else -> {
                        println("Option 2")

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    fun getURI(){
        carArray.forEach{
            if (it.make == basicCarA[0].make && it.model == basicCarA[0].model && it.year == basicCarA[0].year){
                var carImageURI = Uri.parse(it.carImageURI)

                val drawable = Drawable.createFromPath(carImageURI.toString())

                imageView.setImageDrawable(drawable)

                println(carImageURI)
                setImage(carImageURI)
            }
        }
    }

    // Adds cars to the empty cars ArrayList
    fun addCar() {
        var carMakeIntent:String = intent.getStringExtra("carMake")
        var carModelIntent:String = intent.getStringExtra("carModel")
        var carYearIntent:String = intent.getStringExtra("carYear")
        println()
        basicCarA.clear()
        basicCarA.add(basicCar(carMakeIntent, carModelIntent, carYearIntent))
        getURI()
    }
}