package com.cbsa.mcoe.ace.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.cbsa.mcoe.ace.R
import com.cbsa.mcoe.ace.adapters.SearchAdapter
import com.cbsa.mcoe.ace.data_classes.NewDataClassCar
import kotlinx.android.synthetic.main.searchresults.*

class SearchResultsPage: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchresults)
        setupArray()
    }

    private fun setupArray(){
        val searchResultsMake: String = intent.getStringExtra("searchResultsMake")
        val searchResultsModel: String = intent.getStringExtra("searchResultsModel")
        val resultCar =  ArrayList<NewDataClassCar>()
        carArray.forEach {
            if (searchResultsMake == it.make && searchResultsModel == it.model) {
                resultCar.add(it)
            }
        }
        resultCar.sortByDescending { it.year }

        sendDataToRecyclerView(resultCar)
    }

    private fun sendDataToRecyclerView(resultCar: ArrayList<NewDataClassCar>){
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.adapter = SearchAdapter(this, resultCar)
    }

}