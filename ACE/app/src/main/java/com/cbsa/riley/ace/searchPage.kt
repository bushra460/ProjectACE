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

val url = "https://webapp-190113144846.azurewebsites.net/deltaace/v1/manufacturers"
var carArray: ArrayList<Car> = ArrayList()

@UseExperimental(ImplicitReflectionSerializer::class)
class searchPage : Activity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        //TAKE RESULT OF GET CALL AND PARSE THE DATA
        fun workload(data:String) {
            println(data)

            var gson = Gson()
            var manufacturerModel = gson.fromJson(data, ManufacturerModel::class.java)
            val manufacturer1 = JsonParser().parse((gson.toJson(manufacturerModel))).asJsonObject
            val manufacturer2 = manufacturer1.getAsJsonObject("_embedded")
            val manufacturer3 = manufacturer2.getAsJsonArray("manufacturers")
            var manufacturer4:JsonObject
            var manufacturer5:String

            println("data: $manufacturer1")


            manufacturer3.forEach {

                manufacturer4 = manufacturer3.asJsonObject
                manufacturer5 = manufacturer4.get("manufacturerName").asString
                carArray.add(Car(manufacturer5,null,null))
                println(manufacturer5)
            }

//            var testJson = """{"manufacturers" : [{"manufacturerName":"Mazda"},{"modelName":"Mazda 3"},{"year":"2017"}]}"""
//
//            if (testData != null){
//
//            }
        }

        //MAKE GET CALL AND PASS TO WORKLOAD FUNCTION
        val result = GlobalScope.launch {
            val json = URL(url).readText()
            workload(json)
        }


//        val spinner: Spinner = findViewById(R.id.makeSpinner)
//        spinner.onItemSelectedListener = this
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter.createFromResource(
//            this,
//            makeArray.size,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinner.adapter = adapter


        val spinner: Spinner = findViewById(R.id.makeSpinner)
        spinner.onItemSelectedListener = this


        ArrayAdapter<Car>(applicationContext, android.R.layout.simple_spinner_dropdown_item, carArray).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val modelSpinner: Spinner = findViewById(R.id.modelSpinner)
        modelSpinner.onItemSelectedListener = this
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.models_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            modelSpinner.adapter = adapter
        }

        val yearSpinner: Spinner = findViewById(R.id.yearSpinner)
        yearSpinner.onItemSelectedListener = this
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.years_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            yearSpinner.adapter = adapter
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        val parentID = parent.id
        val spinOption = parent.getItemAtPosition(pos)


        if ( makeSpinner.selectedItem == spinOption && spinOption != "Select One"){
            println("Spinner 1: " + spinOption)
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
            var carMake: String
            var carModel: String
            var carYear: String

                carMake = makeSpinner.selectedItem.toString()
                carModel = modelSpinner.selectedItem.toString()
                carYear = yearSpinner.selectedItem.toString()

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

