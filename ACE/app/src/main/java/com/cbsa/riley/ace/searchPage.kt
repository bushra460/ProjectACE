package com.cbsa.riley.ace

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

val makeurl = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/manufacturers"
var carArray = ArrayList<Car>()
var hotspotArray = ArrayList<Hotspot>()
var makeArray = arrayListOf("Select One")
var modelArray = arrayListOf("Select One")
var yearArray = arrayListOf<Any>("Select One")

class searchPage : Activity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        hotspotArray.clear()
        //GET DATA FROM CLOUD
        makeData()
        setMakeSpinner()
        setModelYearSpinners()
    }

    fun setMakeSpinner(){
        println(makeArray)
        val makeSpinner: Spinner = findViewById(R.id.makeSpinner)
        makeSpinner.onItemSelectedListener = this
        ArrayAdapter(this, android.R.layout.simple_spinner_item, makeArray).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            makeSpinner.adapter = adapter
        }
    }

    fun setModelYearSpinners(){
        val modelSpinner: Spinner = findViewById(R.id.modelSpinner)
        modelSpinner.onItemSelectedListener = this
        ArrayAdapter(this, android.R.layout.simple_spinner_item, modelArray).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            modelSpinner.adapter = adapter
        }

        val yearSpinner: Spinner = findViewById(R.id.yearSpinner)
        yearSpinner.onItemSelectedListener = this
        ArrayAdapter(this, android.R.layout.simple_spinner_item, yearArray).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            yearSpinner.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        val spinOption:String = parent.getItemAtPosition(pos).toString()


        if ( makeSpinner.selectedItem == spinOption && spinOption != "Select One"){
            println("Spinner 1: " + spinOption)
            if (modelArray.count() >= 2) {
                modelArray.clear()
                modelArray.add("Select One")
            }
            carArray.forEach{
                if (it.make == spinOption && !modelArray.contains(it.model)){
                    modelArray.add(it.model)
                }
            }
            modelSpinner.visibility = View.VISIBLE
            modelSpinner.setSelection(0)
            yearSpinner.visibility = View.INVISIBLE

        } else if (modelSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 2: " + spinOption)
            if (yearArray.count() >= 2) {
                yearArray.clear()
                yearArray.add("Select One")
            }
            carArray.forEach{
                if (it.model == spinOption && !yearArray.contains(it.year)){
                    yearArray.add(it.year)
                }
            }
            yearSpinner.visibility = View.VISIBLE
            yearSpinner.setSelection(0)

        } else if (yearSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 3: " + spinOption)

        }

        if(makeSpinner.selectedItem != "Select One" && modelSpinner.selectedItem != "Select One" && yearSpinner.selectedItem != "Select One"){
            searchBttnE.isEnabled = true
            var blue = ContextCompat.getColor(this, R.color.CBSA_Blue)
            searchBttnE.setBackgroundColor(blue)
        } else if (spinOption == "Select One"){
            searchBttnE.isEnabled = false
            var gray = ContextCompat.getColor(this, R.color.disabledButton)
            searchBttnE.setBackgroundColor(gray)
        }

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

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        println("onNothingSelected Function in searchPage.kt")
    }

    fun makeData() {
        carArray.clear()
        if (makeArray.count() >= 2) {
            makeArray.clear()
            makeArray.add("Select One")
        }

        //TAKE RESULT OF GET CALL AND PARSE THE DATA
        fun workload(data: String) {
            val gson = Gson()
            val parse = JsonParser().parse(data)

            println("raw parsed data: $parse")

            val manufacturer1 = JsonParser().parse((gson.toJson(parse))).asJsonArray
            manufacturer1.forEach{
                val manufacturer2 = it.asJsonObject
                val manufacturerId = manufacturer2.get("manufacturerId").asString
                val name = manufacturer2.get("name").asString
                val models = manufacturer2.get("models").asJsonArray
                makeArray.add(name)
                models.forEach {
                    val model = it.asJsonObject
                    val modelId = model.get("modelId").asString
                    val modelName = model.get("name").asString
                    val modelYears = model.getAsJsonArray("modelYears")
                    modelYears.forEach{
                        val year = it.asJsonObject
                        val yearId = year.get("modelYearId").asString
                        val yearName = year.get("yearValue").asString

                        val carData = year.get("car").asJsonObject
                        val carDataId = carData.get("carId").asString
                        val carImageArray = carData.get("carImage").asJsonArray

                        carImageArray.forEach{
                            val carImageObj = it.asJsonObject
                            val carImageId = carImageObj.get("carImageId").asInt
                            val carImageURI = carImageObj.get("uri").asString
                            val exteriorImage = carImageObj.get("exteriorImage").asBoolean
                            val active = carImageObj.get("active").asBoolean

                            val hotspotArrayValue = carImageObj.get("hotspotLocations").asJsonArray

                            val newCar = Car(manufacturerId, name, modelId, modelName, yearId, yearName, carImageId, carImageURI, exteriorImage, active)
                            carArray.add(newCar)
                            hotspotArrayValue.forEach{
                                val hotspotObj = it.asJsonObject
                                val xLoc = hotspotObj.get("xLoc").asInt
                                val yLoc = hotspotObj.get("yLoc").asInt
                                val hotspotId = hotspotObj.get("hotspotId").asInt

                                val hotspotDetailsArray = hotspotObj.get("hotspotDetails").asJsonArray

                                hotspotDetailsArray.forEach{
                                    val hotspotDetailsObj = it.asJsonObject
                                    val hotspotUri = hotspotDetailsObj.get("uri").asString
                                    val hotspotNotes = hotspotDetailsObj.get("notes").asString

                                    val newHotspot = Hotspot(carImageId, xLoc, yLoc, hotspotId, hotspotUri, hotspotNotes)
                                    hotspotArray.add(newHotspot)
                                }




                            }
                        }
                    }
                }
            }
            println("carArray data: $carArray")
        }

        //MAKE GET CALL AND PASS TO WORKLOAD FUNCTION
        GlobalScope.launch {
            val json = URL(makeurl).readText()
            workload(json)
        }
    }

}

