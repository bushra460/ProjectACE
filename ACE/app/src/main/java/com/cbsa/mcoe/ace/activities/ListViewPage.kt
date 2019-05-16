package com.cbsa.mcoe.ace.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.cbsa.mcoe.ace.R
import com.cbsa.mcoe.ace.adapters.ListViewAdapter
import com.cbsa.mcoe.ace.data_classes.HotspotDeets
import com.cbsa.mcoe.ace.data_classes.NewDataClassHotspot
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.listview.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class ListViewPage: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)

        setLayout()
        setTitle()
        swiperefresh.setOnRefreshListener { refreshHotspotList() }

    }

    private fun setTitle(){
        val carMake = selectedCar.make
        val carModel = selectedCar.model
        val carYear = selectedCar.year
        listViewToolbar.title = "$carMake $carModel $carYear"
    }

    private fun refreshHotspotList() {
        fun workload(data: String) {
            val gson = Gson()
            val parse = JsonParser().parse(data)
            println("raw parsed data: $parse")
            hotspotArrayList.clear()
            val modelsArrayValue = JsonParser().parse((gson.toJson(parse))).asJsonObject
            val carImageArray = modelsArrayValue.get("carImage").asJsonArray
            carImageArray.forEach {
                val carImageObj = it.asJsonObject
                val carImageId = carImageObj.get("carImageId").asInt
                val exteriorImage = carImageObj.get("exteriorImage").asBoolean

                val hotspotArrayValue = carImageObj.get("hotspotLocations").asJsonArray
                hotspotArrayValue.forEach {
                    val hotspotObj = it.asJsonObject
                    val xLoc = hotspotObj.get("xLoc").asInt
                    val yLoc = hotspotObj.get("yLoc").asInt
                    val hotspotId = hotspotObj.get("hotspotId").asInt
                    val hotspotDesc = hotspotObj.get("hotspotDesc").asString
                    val hotspotDetailsArray = hotspotObj.get("hotspotDetails").asJsonArray

                    hotspotDetailsArray.forEach {
                        val hotspotDetailsObj = it.asJsonObject
                        val hotspotUri = hotspotDetailsObj.get("uri").asString
                        val hotspotNotes = hotspotDetailsObj.get("notes").asString
                        val hotspotDetailsActive = hotspotDetailsObj.get("active").asBoolean

                        val hotspotDetailsArray = ArrayList<HotspotDeets>()
                        val details =
                            HotspotDeets(hotspotUri, hotspotNotes, hotspotDetailsActive)
                        hotspotDetailsArray.add(details)

                        hotspotArrayList.add(
                            NewDataClassHotspot(
                                hotspotId,
                                xLoc,
                                yLoc,
                                hotspotDesc,
                                true,
                                carImageId,
                                hotspotDetailsArray,
                                selectedCar.carId,
                                exteriorImage
                            )
                        )
                    }
                }
            }
            val selectedCarHotspots = ArrayList<NewDataClassHotspot>()
            hotspotArrayList.forEach {
                if (it.carId == selectedCar.carId) {
                    selectedCarHotspots.add(it)
                }
            }
            runOnUiThread {
                recyclerView2.adapter = ListViewAdapter(this, selectedCarHotspots)
                swiperefresh.isRefreshing = false
            }
        }

        GlobalScope.launch {
            println("Car Image Id ${imageArrayList[selectedImage].carImageId}")
            val json =
                URL("https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/cars/${selectedCar.carId}").readText()
            workload(json)
        }
    }

    private fun setLayout(){
        // Creates a vertical Layout Manager
        recyclerView2.layoutManager = LinearLayoutManager(this)

        val selectedCarHotspots = ArrayList<NewDataClassHotspot>()
        hotspotArrayList.forEach {
            if (it.carId == selectedCar.carId){
                selectedCarHotspots.add(it)
            }
        }
        //Access the RecyclerView Adapter and load the data into it
        recyclerView2.adapter = ListViewAdapter(this, selectedCarHotspots)
    }
}
