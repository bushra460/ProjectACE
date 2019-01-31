package com.cbsa.riley.ace

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.imageview.*


var basicCarA: ArrayList<basicCar> = ArrayList()
var exteriorImageURIArray: ArrayList<String> = ArrayList()
var interiorImageURIArray: ArrayList<String> = ArrayList()

class ImageViewPage: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageview)

        addCar()
        var numTab: Int = 0
        val carMake = basicCarA[0].make
        val carModel = basicCarA[0].model
        val carYear = basicCarA[0].year

        toolbar.title = "$carMake $carModel $carYear"
        println("$carMake $carModel $carYear")

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddHotspotPage::class.java)
            if(numTab == 0) {
                intent.putExtra("imageURI", exteriorImageURIArray[0])
            } else {
                intent.putExtra("imageURI", "https://via.placeholder.com/150")
                //intent.putExtra("imageURI", interiorImageURIArray[0])
            }
            startActivity(intent)
        }

//        //HANDLE LISTVIEW BUTTON CLICKS
//        val listViewBttn: Button = listViewBttn
//        listViewBttn.setOnClickListener {
//
//            val intent = Intent(this, SettingsPage::class.java)
//
//            startActivity(intent)
//        }

        val tabLayout = tabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                numTab = tab.position
                when (numTab) {
                    0 -> setExteriorImage()
                    else -> {
                        setInteriorImage()
                    }
                }
            }


            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    fun setExteriorImage(){
        println("Option 1")
        imageView.setImageResource(0)
        Picasso.get().load(exteriorImageURIArray[0]).into(imageView)
    }

    fun setInteriorImage(){
        println("Option 2")
        imageView.setImageResource(0)
        //Picasso.get().load(interiorImageURIArray[0]).into(imageView)
        Picasso.get().load("https://via.placeholder.com/150").into(imageView)
    }

    fun getURI(){
        carArray.forEach{
            if (it.make == basicCarA[0].make && it.model == basicCarA[0].model && it.year == basicCarA[0].year){
                var carImageURI = Uri.parse(it.carImageURI).toString()
                var dataRemoved =  carImageURI.replace("\"","")
                if (it.exteriorImage == true){
                    exteriorImageURIArray.add(dataRemoved)
                    println("Image Added to Exterior Array")
                } else {
                    interiorImageURIArray.add(dataRemoved)
                    println("Image Added to Interior Array")
                }

                println(carImageURI)
                Picasso.get().load(exteriorImageURIArray[0]).into(imageView)
            }
        }
    }

    // Adds cars to the empty cars ArrayList
    fun addCar() {
        var carMakeIntent:String = intent.getStringExtra("carMake")
        var carModelIntent:String = intent.getStringExtra("carModel")
        var carYearIntent:String = intent.getStringExtra("carYear")
        println()
        basicCarA.clear()
        basicCarA.add(basicCar(carMakeIntent, carModelIntent, carYearIntent))
        getURI()
    }
}