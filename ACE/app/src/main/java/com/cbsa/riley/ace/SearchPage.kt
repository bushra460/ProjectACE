package com.cbsa.riley.ace

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

val makeurl = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/manufacturers"
var makeArray = arrayListOf("Select One")
var modelArray = arrayListOf("Select One")
var yearArray = arrayListOf<Any>("Select One")
val REQ_CODE_SPEECH_INPUT = 100
var carArray = ArrayList<NewDataClassCar>()
val newHotspotArray = ArrayList<NewDataClassHotspot>()
val newImageArray = ArrayList<NewDataClassCarImage>()

class searchPage : Activity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        voiceBttn.setOnClickListener {
            modelSpinner.setSelection(0)
            yearSpinner.setSelection(0)
            startVoiceInput()
        }
        voiceBttn.isEnabled = false
        makeSpinner.isEnabled = false
        makeData()
        setMakeSpinner()
        setModelYearSpinners()
        carArray.clear()
        newHotspotArray.clear()
        newImageArray.clear()
    }

    fun setMakeSpinner() {
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

    fun setModelYearSpinners() {
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

        val spinOption: String = parent.getItemAtPosition(pos).toString()


        if (makeSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 1: " + spinOption)

            if (modelArray.count() >= 2) {
                modelArray.clear()
                modelArray.add("Select One")
            }
            carArray.forEach {
                if (spinOption == it.make && !modelArray.contains(it.model)) {
                    modelArray.add(it.model)
                }
            }

            modelSpinner.visibility = View.VISIBLE
            yearSpinner.visibility = View.INVISIBLE
        }
        if (modelSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 2: " + spinOption)
            val selectedItem = yearSpinner.selectedItem


            if (yearArray.count() >= 2) {
                yearArray.clear()
                yearArray.add("Select One")
            }
            carArray.forEach {
                if (it.model == spinOption && !yearArray.contains(it.year)) {
                    yearArray.add(it.year)
                }
            }
            yearSpinner.visibility = View.VISIBLE

            if (!yearArray.contains(selectedItem)) {
                yearSpinner.setSelection(0)
            }

        } else if (yearSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 3: " + spinOption)

        }

        if (makeSpinner.selectedItem != "Select One" && modelSpinner.selectedItem != "Select One") {
            searchBttnE.isEnabled = true
            val blue = ContextCompat.getColor(this, R.color.CBSA_Blue)
            searchBttnE.setBackgroundColor(blue)
        } else if (spinOption == "Select One") {
            searchBttnE.isEnabled = false
            val gray = ContextCompat.getColor(this, R.color.disabledButton)
            searchBttnE.setBackgroundColor(gray)
        }

        //HANDLE SEARCH BUTTON CLICKS
        val searchBttn = searchBttnE
        searchBttn.setOnClickListener {
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

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        println("onNothingSelected Function in SearchPage.kt")
    }

    fun makeData() {
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
            manufacturer1.forEach {
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
                    modelYears.forEach {
                        val year = it.asJsonObject
                        val yearId = year.get("modelYearId").asString
                        val yearName = year.get("yearValue").asString

                        val carData = year.get("car").asJsonObject
                        val carDataId = carData.get("carId").asInt
                        val carImageArray = carData.get("carImage").asJsonArray

                        carImageArray.forEach {
                            val carImageObj = it.asJsonObject
                            val carImageId = carImageObj.get("carImageId").asInt
                            val carImageURI = carImageObj.get("uri").asString
                            val exteriorImage = carImageObj.get("exteriorImage").asBoolean
                            newImageArray.add(NewDataClassCarImage(carImageId, carImageURI, exteriorImage, carDataId))

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

                                    newHotspotArray.add(
                                        NewDataClassHotspot(
                                            hotspotId,
                                            xLoc,
                                            yLoc,
                                            hotspotDesc,
                                            true,
                                            carImageId,
                                            hotspotUri,
                                            hotspotNotes,
                                            carDataId,
                                            exteriorImage
                                        )
                                    )

                                }
                            }
                        }
                        val newCar = NewDataClassCar(
                            carDataId,
                            true,
                            manufacturerId,
                            name,
                            modelId,
                            modelName,
                            yearId,
                            yearName,
                            newImageArray,
                            newHotspotArray
                        )
                        carArray.add(newCar)
                    }
                }
            }
            println("carArray data: $carArray")

            runOnUiThread {
                progress_loader.visibility = View.INVISIBLE
                voiceBttn.isEnabled = true
                makeSpinner.isEnabled = true
            }
        }

        //MAKE GET CALL AND PASS TO WORKLOAD FUNCTION
        GlobalScope.launch {
            val json = URL(makeurl).readText()
            workload(json)
        }
    }

    private fun startVoiceInput() {
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
                            modelArray.forEach {
                                println("result: ${stringList[1]} | modelArrayValue:  $it")

                                var replaceString = it
                                if (it.contains("-")) {
                                    replaceString = it.replace("-", "")
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
                            yearArray.forEach {
                                var replaceString = it.toString()
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

