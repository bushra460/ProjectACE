package com.cbsa.riley.ace

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
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
import java.sql.Timestamp
import java.util.*

class HotspotDetails: AppCompatActivity(){

    var base64String:String = ""
    val imageURL = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/images/add"
    val postURL = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/hotspot-locations/add"
    val carImageIdIntent = imageArrayList[0].carImageId
    val REQ_CODE_SPEECH_INPUT = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotspotdetails)

        floatingActionButton.setOnClickListener {
            startVoiceInput()
        }

        val carMake = selectedCar.make
        val carModel = selectedCar.model
        val carYear = selectedCar.year

        toolbar.title = "$carMake $carModel $carYear"

        Picasso.get().load("https://via.placeholder.com/150").into(hotspotDetailsImageView)
        hotspotDetailsImageView.setOnClickListener{
            takePictureIntent()
        }

        finishBttnClick()
    }

    fun finishBttnClick() {
        finishBttn.setOnClickListener {
            finishBttn.isEnabled = false
            val date = Date()
            val ts = Timestamp(date.time)
            val imagePOST = ImagePOST(base64String, "$ts-${selectedCar.carId}")
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
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {


                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    println("The result is: $result")
                    val editable = SpannableStringBuilder(result[0])
                    println(editable)

                    notesText.text = editable
                }
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
             hotspotArrayList.add(newHotspot)

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


    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say the make, model and year of the car you are searching for")
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {

        }
    }
}

