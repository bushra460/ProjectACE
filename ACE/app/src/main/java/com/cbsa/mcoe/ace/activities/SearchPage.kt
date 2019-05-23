package com.cbsa.mcoe.ace.activities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.cbsa.mcoe.ace.R
import com.cbsa.mcoe.ace.data_classes.NewDataClassCar
import com.cbsa.mcoe.ace.data_classes.NewDataClassCarImage
import com.cbsa.mcoe.ace.data_classes.NewDataClassHotspot
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

const val makeurl = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/manufacturers"
var makeArray = arrayListOf("Select One")
var modelArray = arrayListOf("Select One")
var yearArray = arrayListOf("Select One")
const val REQ_CODE_SPEECH_INPUT = 100
var carArray = ArrayList<NewDataClassCar>()
val newHotspotArray = ArrayList<NewDataClassHotspot>()
val newImageArray = ArrayList<NewDataClassCarImage>()

class SearchPage : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        voiceBttn.setOnClickListener {
            startVoiceInput()
        }
        checkConnection()
        disableButtonsOnLoad()
        addListenerToSpinners()
        clearArrays()
    }

    //checks the connection then calls makeData()
    private fun checkConnection(){
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        if (isConnected) {
            makeData()
        } else if (!isConnected){
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            progress_loader.visibility = View.INVISIBLE
        }
    }

    //disables buttons during loading
    private fun disableButtonsOnLoad(){
        voiceBttn.isEnabled = false
        makeSpinner.isEnabled = false
    }

    //enables buttons after loading
    private fun enableButtonsAfterLoad(){
        progress_loader.visibility = View.INVISIBLE
        voiceBttn.isEnabled = true
        makeSpinner.isEnabled = true
    }

    //adds listeners to the dropdowns
    private fun addListenerToSpinners() {
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

    //clears arrays
    private fun clearArrays(){
        carArray.clear()
        newHotspotArray.clear()
        newImageArray.clear()
    }

    //runs when a dropdown item gets selected
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        val spinOption: String = parent.getItemAtPosition(pos).toString()

        if (makeSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 1: $spinOption")
            val selectedItem = modelSpinner.selectedItem

            resetDropdownArray(modelArray)

            carArray.forEach {
                if (spinOption == it.make && !modelArray.contains(it.model)) {
                    modelArray.add(it.model)
                }
            }

            if (!modelArray.contains(selectedItem)) {
                modelSpinner.setSelection(0)
            }

            modelSpinner.visibility = View.VISIBLE
            yearSpinner.visibility = View.INVISIBLE
        }

        if (modelSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 2: $spinOption")
            val selectedItem = yearSpinner.selectedItem
            resetDropdownArray(yearArray)

            carArray.forEach {
                if (it.model == spinOption && !yearArray.contains(it.year)) {
                    yearArray.add(it.year)
                }
            }
            yearArray.sortByDescending { it }
            yearSpinner.visibility = View.VISIBLE
            if (!yearArray.contains(selectedItem)) {
                yearSpinner.setSelection(0)
            }
        } else if (yearSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 3: $spinOption")
        }

        if (makeSpinner.selectedItem != "Select One" && modelSpinner.selectedItem != "Select One") { enableButton(searchBttnE) } else if (spinOption == "Select One") { disableButton(searchBttnE) }

        searchBttnE.setOnClickListener { searchButtonClicked() }

    }

    //enables button
    private fun enableButton(button: Button) {
        button.isEnabled = true
        val blue = ContextCompat.getColor(this, R.color.CBSA_Blue)
        button.setBackgroundColor(blue)
    }

    //diables button
    private fun disableButton(button: Button) {
        button.isEnabled = false
        val gray = ContextCompat.getColor(this, R.color.disabledButton)
        button.setBackgroundColor(gray)
    }

    //checks if array is full then clears it if it has more than "select one"
    private fun resetDropdownArray(array: ArrayList<String>){
        if (array.count() >= 2) {
            array.clear()
            array.add("Select One")
        }
    }

    //checks car make model year and Id and sends them with the intent to imageviewpage or searchresults depending on how many dropdowns are filled
    private fun searchButtonClicked(){
        val carMake: String = makeSpinner.selectedItem.toString()
        val carModel: String = modelSpinner.selectedItem.toString()
        val carYear: String = yearSpinner.selectedItem.toString()

        if (makeSpinner.selectedItem != "Select One" && modelSpinner.selectedItem != "Select One" && yearSpinner.selectedItem != "Select One"){
            val intent = Intent(this, ImageViewPage::class.java)
            carArray.forEach {
                if (it.make == carMake && it.model == carModel && it.year == carYear) {
                    intent.putExtra("carId", it.carId)
                }
            }
            startActivity(intent)
        } else if (makeSpinner.selectedItem != "Select One" && modelSpinner.selectedItem != "Select One"){
            var index = 0
            carArray.forEach {
                if (it.make == makeSpinner.selectedItem && it.model == modelSpinner.selectedItem){
                    index += 1
                }
            }
            if (index > 1) {
                val intent = Intent(this, SearchResultsPage::class.java)
                intent.putExtra("searchResultsMake", makeSpinner.selectedItem.toString())
                intent.putExtra("searchResultsModel", modelSpinner.selectedItem.toString())

                startActivity(intent)
            } else if (index == 1) {
                val intent = Intent(this, ImageViewPage::class.java)
                carArray.forEach {
                    if (it.make == makeSpinner.selectedItem && it.model == modelSpinner.selectedItem){
                        intent.putExtra("carId", it.carId)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    //runs if nothing in the dropdown is selected
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        println("onNothingSelected Function in SearchPage.kt")
    }

    //makes the get call for data and puts it in the data class structure
    private fun makeData() {
        if (makeArray.count() >= 2) {
            makeArray.clear()
            makeArray.add("Select One")
        }

        //TAKE RESULT OF GET CALL AND PARSE THE DATA
        fun workload(data: String) {
            val gson = Gson()
            val parse = JsonParser().parse(data)

            println("raw parsed data: $parse")

            val manufacturerArray = JsonParser().parse((gson.toJson(parse))).asJsonArray
            manufacturerArray.forEach { manufacturerJson ->
                val manufacturerObj = manufacturerJson.asJsonObject
                val manufacturerId = manufacturerObj.get("manufacturerId").asString
                val name = manufacturerObj.get("name").asString
                val models = manufacturerObj.get("models").asJsonArray
                makeArray.add(name)
                models.forEach { modelsJson ->
                    val model = modelsJson.asJsonObject
                    val modelId = model.get("modelId").asString
                    val modelName = model.get("name").asString
                    val modelYears = model.getAsJsonArray("modelYears")
                    modelYears.forEach { modelYearsJson ->
                        val year = modelYearsJson.asJsonObject
                        val yearId = year.get("modelYearId").asString
                        val yearName = year.get("yearValue").asString
                        val carData = year.get("car").asJsonObject
                        val carDataId = carData.get("carId").asInt
                        val newCar = NewDataClassCar(carDataId, true, manufacturerId, name, modelId, modelName, yearId, yearName, null, null)
                        carArray.add(newCar)
                    }
                }
            }
            println("carArray data: $carArray")
            runOnUiThread { enableButtonsAfterLoad() }
        }

        //MAKE GET CALL AND PASS TO WORKLOAD FUNCTION
        GlobalScope.launch {
            try {

                URL(makeurl).run {
                    openConnection().run {
                        val httpURLConnection = this as HttpURLConnection

                        httpURLConnection.requestMethod = "GET"
                        httpURLConnection.setRequestProperty("charset", "utf-8")
                        httpURLConnection.setRequestProperty("Content-Type", "application/json")

                        if (httpURLConnection.responseCode != 200) {
                            Toast.makeText(this@SearchPage, "Server Error, Please restart application", Toast.LENGTH_SHORT).show()
                        }

                        println("server response code " + httpURLConnection.responseCode)
                        workload(inputStream.bufferedReader().readText())
                    }
                }

            } catch (e: Error){
                Toast.makeText(this@SearchPage, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    //clears model and year dropdowns and starts voice prompt
    private fun startVoiceInput() {
        modelSpinner.setSelection(0)
        yearSpinner.setSelection(0)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Please say the make, model and year of the car you are searching for"
        )
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {

        }

    }

    //handles voiceInput results and dropdown item clicks
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    println("The result is: $result")
                    var resultString = result[0]
                    if (result[0].contains("-")) {
                        resultString = result[0].replace("-", "")
                    }
                    val stringList = resultString.split(" ")

                    if (stringList.size in 1..3) {
                        var i = 0
                        var set = false
                        if (!set) {
                            makeArray.forEach {
                                println("result: ${stringList[0]} | makeArrayValue:  $it")

                                var replaceString = it
                                if (it.contains("-")) {
                                    replaceString = it.replace("-", "")
                                }

                                if (stringList[0] == replaceString) {
                                    println(replaceString)
                                    makeSpinner.setSelection(i)
                                    set = true
                                }
                                if (i == makeArray.size - 1 && !set) {
                                    Toast.makeText(this, "No match found, please try again", Toast.LENGTH_LONG).show()
                                }
                                i++
                            }
                        }
                    }

                    if (stringList.size in 2..3) {
                        modelArray.clear()
                        modelArray.add("Select One")
                        carArray.forEach {
                            if (makeSpinner.selectedItem == it.make && !modelArray.contains(it.model)) {
                                modelArray.add(it.model)
                            }
                        }

                        var set = false
                        var index = 0
                        if (!set) {
                            modelArray.forEach { model ->
                                println("result: ${stringList[1]} | modelArrayValue:  $model")

                                var replaceString = model
                                if (model.contains("-")) {
                                    replaceString = model.replace("-", "")
                                }

                                if (stringList[1] == replaceString) {
                                    println(replaceString)

                                    modelSpinner.visibility = View.VISIBLE
                                    modelSpinner.setSelection(index)
                                    set = true

                                    yearArray.clear()
                                    yearArray.add("Select One")
                                    carArray.forEach {
                                        if (modelSpinner.selectedItem == it.model) {
                                            yearArray.add(it.year)
                                        }
                                    }
                                } else if (index == modelArray.size - 1 && !set) {
                                    Toast.makeText(this, "No match found, please try again", Toast.LENGTH_LONG).show()
                                }
                                index++
                            }
                        }
                    }

                    if (stringList.size == 3) {
                        var index2 = 0
                        var set = false
                        if (!set) {
                            yearArray.forEach { year ->
                                var replaceString = year
                                if (replaceString.contains("-")) {
                                    replaceString = replaceString.replace("-", "")
                                }

                                println("result: ${stringList[2]} | yearArrayValue:  $replaceString")
                                if (stringList[2] == replaceString) {
                                    println(replaceString)
                                    yearSpinner.setSelection(index2)
                                    set = true
                                } else if (index2 == yearArray.size - 1 && !set) {
                                    Toast.makeText(this, "No match found, please try again", Toast.LENGTH_LONG).show()
                                }
                                index2++
                            }
                        }
                    }
                }
            }
        }
    }
}

