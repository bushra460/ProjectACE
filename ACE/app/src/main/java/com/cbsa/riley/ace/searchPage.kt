package com.cbsa.riley.ace

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import java.net.URL

val makeurl = "https://webapp-190120211610.azurewebsites.net/deltaace/v1/manufacturers"
var modelurl = "https://webapp-190120211610.azurewebsites.net/deltaace/v1/models"
var yearurl = "https://webapp-190120211610.azurewebsites.net/deltaace/v1/modelYear"
var makeArray = arrayListOf<String>("Select One")
var modelArray = arrayListOf<String>("Select One")
var yearArray = arrayListOf<Any>("Select One")


@UseExperimental(ImplicitReflectionSerializer::class)
class searchPage : Activity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        if (makeArray.count() >= 2) {
            makeArray.clear()
            makeArray.add("Select One")
        }
        if (modelArray.count() >= 2) {
            modelArray.clear()
            modelArray.add("Select One")
        }
        if (yearArray.count() >= 2) {
            yearArray.clear()
            yearArray.add("Select One")
        }


        makeData()

        println(makeArray)
        val makeSpinner: Spinner = findViewById(R.id.makeSpinner)
        makeSpinner.onItemSelectedListener = this
        ArrayAdapter(this, android.R.layout.simple_spinner_item, makeArray).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            makeSpinner.adapter = adapter
        }

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

    fun makeData(){
        //TAKE RESULT OF GET CALL AND PARSE THE DATA
        fun workload(data:String) {
            println(data)
            var gson = Gson()
            var manufacturerModel = gson.fromJson(data, ManufacturerModel::class.java)
            val manufacturer1 = JsonParser().parse((gson.toJson(manufacturerModel))).asJsonObject
            val manufacturer2 = manufacturer1.getAsJsonObject("_embedded")
            val manufacturer3 = manufacturer2.getAsJsonArray("manufacturers")
            var manufacturer4: JsonObject
            var manufacturer5:String
            println("data: $manufacturer1")
            manufacturer3.forEach {
                manufacturer4 = it.asJsonObject
                manufacturer5 = manufacturer4.get("name").asString
                makeArray.add(manufacturer5)
                println(makeArray)
            }
        }

        //MAKE GET CALL AND PASS TO WORKLOAD FUNCTION
        GlobalScope.launch {
            val json = URL(makeurl).readText()
            workload(json)
        }
    }

    fun modelData(pos: Int){
        //TAKE RESULT OF GET CALL AND PARSE THE DATA
        fun workload(data:String) {
            println(data)
            var gson = Gson()
            var manufacturerModel = gson.fromJson(data, ManufacturerModel::class.java)
            val manufacturer1 = JsonParser().parse((gson.toJson(manufacturerModel))).asJsonObject
            val manufacturer2 = manufacturer1.getAsJsonObject("_embedded")
            val manufacturer3 = manufacturer2.getAsJsonArray("models")
            var manufacturer4: JsonObject
            var manufacturer5:String
            println("data: $manufacturer1")
            manufacturer3.forEach {
                manufacturer4 = it.asJsonObject
                manufacturer5 = manufacturer4.get("name").asString
                modelArray.add(manufacturer5)
                println(modelArray)

                var year5 = manufacturer4.getAsJsonArray("modelYears")

                year5.forEach{
                    var year6 = it.asJsonObject
                    var year7:Int = year6.get("yearValue").asInt
                    yearArray.add(year7)
                }


            }



        }

        //MAKE GET CALL AND PASS TO WORKLOAD FUNCTION
        GlobalScope.launch {
            modelArray.clear()
            modelArray.add("Select One")
            modelurl = makeurl + "/$pos/models"
            val json = URL(modelurl).readText()
            workload(json)
        }
    }

    fun yearData(pos: Int){
        //TAKE RESULT OF GET CALL AND PARSE THE DATA
        fun workload(data:String) {
            println(data)
            var gson = Gson()
            var manufacturerModel = gson.fromJson(data, ManufacturerModel::class.java)
            val manufacturer1 = JsonParser().parse((gson.toJson(manufacturerModel))).asJsonObject
            val manufacturer2 = manufacturer1.getAsJsonObject("_embedded")
            val manufacturer3 = manufacturer2.getAsJsonArray("models")
            var manufacturer4: JsonObject
            var manufacturer5:String
            println("data: $manufacturer1")
            manufacturer3.forEach {
                manufacturer4 = it.asJsonObject
                manufacturer5 = manufacturer4.get("name").asString
                modelArray.add(manufacturer5)
                println(modelArray)
            }
        }

        //MAKE GET CALL AND PASS TO WORKLOAD FUNCTION
        GlobalScope.launch {
            modelArray.clear()
            modelArray.add("Select One")
            val json = URL(makeurl + "/$pos/models").readText()
            workload(json)
        }
    }


    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        val spinOption:String = parent.getItemAtPosition(pos).toString()


        if ( makeSpinner.selectedItem == spinOption && spinOption != "Select One"){
            println("Spinner 1: " + spinOption)
            modelData(pos)
            modelSpinner.visibility = View.VISIBLE

        } else if (modelSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 2: " + spinOption)
            yearSpinner.visibility = View.VISIBLE

        } else if (yearSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 3: " + spinOption)

        }

        if(makeSpinner.selectedItem != "Select One" && modelSpinner.selectedItem != "Select One" && yearSpinner.selectedItem != "Select One"){
            searchBttnE.isEnabled = true
        } else if (spinOption == "Select One"){
            searchBttnE.isEnabled = false
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

}

