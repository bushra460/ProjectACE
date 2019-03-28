package com.cbsa.riley.ace

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonParser
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

    var base64String1:String = ""
    var base64String2:String = ""
    var base64String3:String = ""
    var base64String4:String = ""
    var base64String5:String = ""
    val imageURL = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/images/add"
    val postURL = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/hotspot-locations/add"
    val carImageIdIntent = imageArrayList[selectedImage].carImageId
    val REQ_CODE_SPEECH_INPUT = 100
    var imageSet = false
    var imageClicked = "hotspotDetailsImageView"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotspotdetails)

        floatingActionButton.setOnClickListener {
            startVoiceInput()
        }

        titleText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (imageSet && !titleText.text.isEmpty()){
                    val colorValue = ContextCompat.getColor(this@HotspotDetails, android.R.color.white)
                    finishBttn.setTextColor(colorValue)
                    finishBttn.isEnabled = true
                } else {
                    val colorValue = ContextCompat.getColor(this@HotspotDetails, R.color.disabledButton)
                    finishBttn.setTextColor(colorValue)
                    finishBttn.isEnabled = false
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })


        hotspotDetailsImageView.setOnClickListener{
            imageClicked = "hotspotDetailsImageView"
            takePictureIntent()
        }
//        hotspotDetailsImageViewSmallTR.setOnClickListener{
//            imageClicked = "hotspotDetailsImageViewSmallTR"
//            takePictureIntent()
//        }
//        hotspotDetailsImageViewSmallTL.setOnClickListener{
//            imageClicked = "hotspotDetailsImageViewSmallTL"
//            takePictureIntent()
//        }
//        hotspotDetailsImageViewSmallBR.setOnClickListener{
//            imageClicked = "hotspotDetailsImageViewSmallBR"
//            takePictureIntent()
//        }
//        hotspotDetailsImageViewSmallBL.setOnClickListener{
//            imageClicked = "hotspotDetailsImageViewSmallBL"
//            takePictureIntent()
//        }

        finishBttnClick()
    }

    fun finishBttnClick() {
        finishBttn.setOnClickListener {
            val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnected == true
            if (isConnected) {
                finishBttn.isEnabled = false
                runOnUiThread { progress_loader.visibility = View.VISIBLE }
                val date = Date()
                val ts = Timestamp(date.time)
                val imagePOST = ImagePOST(base64String1, "$ts-${selectedCar.carId}.jpg")
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
            } else if (!isConnected){
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
                progress_loader.visibility = View.INVISIBLE
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
                val extras = data?.extras
                val imageBitmap = extras?.get("data") as Bitmap

                when (imageClicked) {
                    "hotspotDetailsImageView" -> hotspotDetailsImageView.setImageBitmap(imageBitmap)
                    "hotspotDetailsImageViewSmallBL" -> hotspotDetailsImageViewSmallBL.setImageBitmap(imageBitmap)
                    "hotspotDetailsImageViewSmallBR" -> hotspotDetailsImageViewSmallBR.setImageBitmap(imageBitmap)
                    "hotspotDetailsImageViewSmallTL" -> hotspotDetailsImageViewSmallTL.setImageBitmap(imageBitmap)
                    "hotspotDetailsImageViewSmallTR" -> hotspotDetailsImageViewSmallTR.setImageBitmap(imageBitmap)
                }
                when (imageClicked) {
                    "hotspotDetailsImageView" -> this.base64String1 = getBase64String(imageBitmap)
                    "hotspotDetailsImageViewSmallBL" -> this.base64String2 = getBase64String(imageBitmap)
                    "hotspotDetailsImageViewSmallBR" -> this.base64String3 = getBase64String(imageBitmap)
                    "hotspotDetailsImageViewSmallTL" -> this.base64String4 = getBase64String(imageBitmap)
                    "hotspotDetailsImageViewSmallTR" -> this.base64String5 = getBase64String(imageBitmap)
                }




                imageSet = true
                if (titleText.text.toString() != ""){
                    val colorValue = ContextCompat.getColor(this, android.R.color.white)
                    finishBttn.setTextColor(colorValue)
                    finishBttn.isEnabled = true
                }
            }
        }
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    println("The result is: $result")
                    val editable = SpannableStringBuilder(result[0])
                    println(editable)

                    if (titleText.text.isEmpty()){
                        titleText.text = editable
                    } else {
                        notesText.text = editable
                    }
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
         val carImageId = CarImage(carImageIdIntent)

         val xLoc = intent.getIntExtra("xLoc", 0)
         val yLoc = intent.getIntExtra("yLoc", 0)
         val title = titleText.text.toString()


         val hotspotDetails = ArrayList<HotspotDeets>()
         val hotspotDeets = HotspotDeets(uri, notes, true)
         hotspotDetails.add(hotspotDeets)

         val hotspotPost = HotspotPost(carImageId, xLoc, yLoc, title, true, hotspotDetails)

         fun workload(data: String) {
             val gson = Gson()
             val parse = JsonParser().parse(data)
             println(parse)

             val returnedObject = JsonParser().parse((gson.toJson(parse))).asJsonObject

             val hotspotId = returnedObject.get("hotspotId").asInt
             val newXloc = returnedObject.get("xLoc").asInt
             val newYloc = returnedObject.get("yLoc").asInt
             val title = titleText.text.toString()
             val newHotspot = NewDataClassHotspot(hotspotId,newXloc,newYloc,title, true, carImageIdIntent, uri, notes, selectedCar.carId, exterior)
             hotspotArrayList.add(newHotspot)

             println("returned hotspot POST data $returnedObject")
             runOnUiThread { progress_loader.visibility = View.INVISIBLE }
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


    fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start talking")
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {

        }
    }
}

