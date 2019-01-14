package com.cbsa.riley.ace

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.search.*

class searchPage : Activity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        val spinner: Spinner = findViewById(R.id.makeSpinner)
        spinner.onItemSelectedListener = this
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.makes_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
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

        val trimSpinner: Spinner = findViewById(R.id.trimSpinner)
        trimSpinner.onItemSelectedListener = this
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.trims_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            trimSpinner.adapter = adapter
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
            trimSpinner.visibility = View.VISIBLE

        } else if (trimSpinner.selectedItem == spinOption && spinOption != "Select One") {
            println("Spinner 4: " + spinOption)

        }

        if(makeSpinner.selectedItem != "Select One" && modelSpinner.selectedItem != "Select One" && yearSpinner.selectedItem != "Select One"){
            searchBttnE.isEnabled = true
        } else if (spinOption == "Select One"){
            searchBttnE.isEnabled = false
        }

        //HANDLE ENGLISH BUTTON CLICKS

        val searchBttn = searchBttnE
        searchBttn.setOnClickListener {
            var carMake: String
            var carModel: String
            var carYear: String
            var carTrim: String = ""
            if (trimSpinner.selectedItem.toString() != "--Not Sure--"){
                carMake = makeSpinner.selectedItem.toString()
                carModel = modelSpinner.selectedItem.toString()
                carYear = yearSpinner.selectedItem.toString()
                carTrim = trimSpinner.selectedItem.toString()
            } else {
                carMake = makeSpinner.selectedItem.toString()
                carModel = modelSpinner.selectedItem.toString()
                carYear = yearSpinner.selectedItem.toString()
            }
            val intent = Intent(this, SearchResultsPage::class.java)
            intent.putExtra("carMake", carMake)
            intent.putExtra("carModel", carModel)
            intent.putExtra("carYear", carYear)
            intent.putExtra("carTrim", carTrim)

            startActivity(intent)
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        println("onNothingSelected Function in searchPage.kt")
    }

}

