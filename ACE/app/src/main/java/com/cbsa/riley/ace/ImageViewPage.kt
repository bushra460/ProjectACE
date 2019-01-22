package com.cbsa.riley.ace

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.imageview.*
import kotlinx.android.synthetic.main.search.*

val cars: ArrayList<Car> = ArrayList()


class ImageViewPage: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageview)
        addCar()
        toolbar.title = cars[0].make
        println(cars)


    }

    fun bttnClicks(){
        //HANDLE SEARCH BUTTON CLICKS
        val searchBttn = searchBttnE
        searchBttn.setOnClickListener {
            var carMake: String = makeSpinner.selectedItem.toString()
            var carModel: String = modelSpinner.selectedItem.toString()
            var carYear: String = yearSpinner.selectedItem.toString()

            val intent = Intent(this, ImageViewPage::class.java)
            intent.putExtra("carMake", carMake)
            intent.putExtra("carModel", carModel)
            intent.putExtra("carYear", carYear)

            startActivity(intent)
        }
    }


    // Adds cars to the empty cars ArrayList
    fun addCar() {
        val carMake:String = intent.getStringExtra("carMake")
        val carModel:String = intent.getStringExtra("carModel")
        val carYear:String = intent.getStringExtra("carYear")
        cars.clear()
        cars.add(Car(carMake, carModel, carYear))
    }
}