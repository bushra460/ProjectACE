package com.cbsa.riley.ace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.searchresults.*

class ListViewPage: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)
        // Creates a vertical Layout Manager
        recyclerView1.layoutManager = LinearLayoutManager(this)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        //rv_animal_list.layoutManager = GridLayoutManager(this, 2)

         //Access the RecyclerView Adapter and load the data into it

        recyclerView1.adapter = SearchAdapter(hotspotArray)

    }


}