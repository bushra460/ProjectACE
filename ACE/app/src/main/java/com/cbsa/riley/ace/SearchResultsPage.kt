package com.cbsa.riley.ace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.searchresults.*

class SearchResultsPage: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchresults)
        recyclerView1.layoutManager = LinearLayoutManager(this)

        val searchResultsMake: String = intent.getStringExtra("searchResultsMake")
        val searchResultsModel: String = intent.getStringExtra("searchResultsModel")

        val resultCar =  ArrayList<NewDataClassCar>()
        carArray.forEach {
            if (searchResultsMake == it.make && searchResultsModel == it.model) {
                resultCar.add(it)
            }
        }

        resultCar.sortByDescending { it.year }

        recyclerView1.adapter = SearchAdapter(this, resultCar)
    }

}