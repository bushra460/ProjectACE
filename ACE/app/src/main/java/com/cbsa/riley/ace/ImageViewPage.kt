package com.cbsa.riley.ace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

val cars: ArrayList<Car> = ArrayList()


class ImageViewPage: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageview)

        addCar()





    }


    // Adds animals to the empty animals ArrayList
    fun addCar() {
        val carMake:String = intent.getStringExtra("carMake")
        val carModel:String = intent.getStringExtra("carModel")
        val carYear:String = intent.getStringExtra("carYear")
        val carTrim:String = intent.getStringExtra("carTrim")
        cars.add(Car(carMake, carModel, carYear))
        cars.add(Car("volkswagen", "golf", "1998"))
    }
}