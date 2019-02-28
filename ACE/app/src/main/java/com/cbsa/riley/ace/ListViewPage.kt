package com.cbsa.riley.ace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.listview.*

class ListViewPage: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)
        // Creates a vertical Layout Manager
        recyclerView2.layoutManager = LinearLayoutManager(this)

        val carMake = selectedCar.make
        val carModel = selectedCar.model
        val carYear = selectedCar.year
        listViewToolbar.title = "$carMake $carModel $carYear"

        //Access the RecyclerView Adapter and load the data into it
        recyclerView2.adapter = ListViewAdapter(this ,hotspotArrayList)

    }
}
