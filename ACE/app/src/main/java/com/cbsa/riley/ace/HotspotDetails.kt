package com.cbsa.riley.ace

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.hotspotdetails.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL


class HotspotDetails: AppCompatActivity(){

    var base64String:String = ""
    val imageURL = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/images/add"
    val postURL = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/hotspot-locations/add"
    val carImageIdIntent = imageArrayList[0].carImageId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotspotdetails)

        val carMake = selectedCar.make
        val carModel = selectedCar.model
        val carYear = selectedCar.year

        toolbar.title = "$carMake $carModel $carYear"

        Picasso.get().load("https://via.placeholder.com/150").into(hotspotDetailsImageView)
        hotspotDetailsImageView.setOnClickListener{
            takePictureIntent()
        }

        finishBttnClick()

//        notesText.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {}
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {  }
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//                if (notesText.text != null) {
//                    val colorValue = ContextCompat.getColor(this@HotspotDetails, android.R.color.white)
//                    finishBttn.setTextColor(colorValue)
//                    finishBttn.isEnabled = true
//                } else {
//                    val colorValue = ContextCompat.getColor(this@HotspotDetails, android.R.color.white)
//                    finishBttn.setTextColor(colorValue)
//                    finishBttn.isEnabled = false
//                }
//            }
//        })
    }

    fun finishBttnClick() {
        finishBttn.setOnClickListener {
            finishBttn.isEnabled = false

            val imagePOST = ImagePOST(base64String, "rileysimages.jpg")
            fun workload(data: String) {
                val gson = Gson()
                val parse = JsonParser().parse(data)
                println("URL returned by API $parse")

                val returnedObject = JsonParser().parse((gson.toJson(parse))).asJsonObject
                val imageURL = returnedObject.get("imageUri").toString()
                val dataRemoved =  imageURL.replace("\"","")

                postData(dataRemoved)
            }


            GlobalScope.launch {
                URL(imageURL).run {
                    openConnection().run {
                        val httpURLConnection = this as HttpURLConnection
                        httpURLConnection.requestMethod = "POST"
                        httpURLConnection.setRequestProperty("charset", "utf-8")
                        httpURLConnection.setRequestProperty("Content-Type", "application/json")

                        val gson = Gson()
                        val outputStream = DataOutputStream(httpURLConnection.outputStream)
                        outputStream.writeBytes(gson.toJson(imagePOST))
                        workload(inputStream.bufferedReader().readText())
                    }
                }
            }
        }
    }

    fun takePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            1 -> {
                val extras = data?.getExtras()
                val imageBitmap = extras?.get("data") as Bitmap

                base64String = getBase64String(imageBitmap)

                hotspotDetailsImageView.setImageBitmap(imageBitmap)

                val colorValue = ContextCompat.getColor(this, android.R.color.white)
                finishBttn.setTextColor(colorValue)
                finishBttn.isEnabled = true

            }
        }
    }

    private fun getBase64String(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val imageBytes = baos.toByteArray()

        return Base64.encodeToString(imageBytes, Base64.NO_WRAP)
    }

     fun postData(uri: String) {
         val notes = notesText.text.toString()
         val carImageId = CarImage(carImageId = carImageIdIntent)

         val xLoc = intent.getIntExtra("xLoc", 0)
         val yLoc = intent.getIntExtra("yLoc", 0)


         val hotspotDetails = ArrayList<HotspotDeets>()
         val hotspotDeets = HotspotDeets(uri, notes, true)
         hotspotDetails.add(hotspotDeets)

         val hotspotPost = HotspotPost(carImageId, xLoc, yLoc, "Front Exterior", true, hotspotDetails)

         fun workload(data: String) {
             val gson = Gson()
             val parse = JsonParser().parse(data)
             println(parse)

             val returnedObject = JsonParser().parse((gson.toJson(parse))).asJsonObject

             val hotspotId = returnedObject.get("hotspotId").asInt
             val newXloc = returnedObject.get("xLoc").asInt
             val newYloc = returnedObject.get("yLoc").asInt
             val newHotspot = NewDataClassHotspot(hotspotId,newXloc,newYloc,"Front Exterior", true, carImageIdIntent, uri, notes, selectedCar.carId, exterior)
             selectedCar.hotspotArrayList!!.add(newHotspot)

             println("returned hotspot POST data $returnedObject")
             sendIntent()
         }


         GlobalScope.launch {
                 URL(postURL).run {
                     openConnection().run {
                         val httpURLConnection = this as HttpURLConnection

                         httpURLConnection.requestMethod = "POST"
                         httpURLConnection.setRequestProperty("charset", "utf-8")
                         httpURLConnection.setRequestProperty("Content-Type", "application/json")

                         val gson = Gson()
                         val data = gson.toJson(hotspotPost)
                         val outputStream = DataOutputStream(httpURLConnection.outputStream)

                         outputStream.writeBytes(data)
                         println("server response code " + httpURLConnection.responseCode)
                         workload(inputStream.bufferedReader().readText())
                     }
                 }
         }
     }

    fun sendIntent(){
        val intent = Intent(this, ImageViewPage::class.java)
        startActivity(intent)
    }

}

