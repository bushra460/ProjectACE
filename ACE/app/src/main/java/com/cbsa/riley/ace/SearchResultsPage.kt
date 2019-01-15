package com.cbsa.riley.ace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.searchresults.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL


class SearchResultsPage : AppCompatActivity() {

    val cars: ArrayList<Car> = ArrayList()
    val url = "https://webapp-190113144846.azurewebsites.net/deltaace/v1/manufacturers"

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

        suspend fun workload(s:String) {
            println(s)

            val toObj = JSONObject.quote(s)
            println("toString $toObj")

//            var jsonArray = JSONArray(s)
//            for (jsonIndex in 0..(jsonArray.length() - 1)) {
//                println("JSON: " + jsonArray.getJSONObject(jsonIndex).getString("manufacturerName"))
//            }
        }

        val result = GlobalScope.launch {
            val json = URL(url).readText()
            workload(json)
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