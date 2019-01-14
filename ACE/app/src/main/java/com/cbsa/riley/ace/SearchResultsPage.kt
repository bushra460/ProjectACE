package com.cbsa.riley.ace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.cbsa.riley.ace.R.id.linearLayoutM
import kotlinx.android.synthetic.main.searchresults.*

class SearchResultsPage : AppCompatActivity() {

    val cars: ArrayList<Car> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchresults)

        addAnimals()

        // Creates a vertical Layout Manager
        recyclerView1.layoutManager = LinearLayoutManager(this)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        // rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it

        recyclerView1.adapter = SearchAdapter(cars)


        var i = 0
        println("Help" )
        //var count:Int = cardView.childCount
        println("Help" )
        while (i < 2){
            i++
            println("Help" + i)
            linearLayoutM
      }



    }

    // Adds animals to the empty animals ArrayList
    fun addAnimals() {
        val carMake:String = intent.getStringExtra("carMake")
        val carModel:String = intent.getStringExtra("carModel")
        val carYear:String = intent.getStringExtra("carYear")
        val carTrim:String = intent.getStringExtra("carTrim")
        cars.add(Car(carMake, carModel, carYear, carTrim))
        cars.add(Car("volkswagen", "golf", "1998", null))

    }
}