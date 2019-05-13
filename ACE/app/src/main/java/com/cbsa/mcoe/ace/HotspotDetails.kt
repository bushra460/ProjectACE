package com.cbsa.mcoe.ace

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import android.widget.Toast
import com.cbsa.mcoe.ace.data_classes.*
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.hotspotdetails.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HotspotDetails: AppCompatActivity(){

    var base64String1:String = ""
    var base64String2:String = ""
    var base64String3:String = ""
    var base64String4:String = ""
    var base64String5:String = ""
    var base64StringsArray = ArrayList<String>()
    val imageURL = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/images/add"
    val postURL = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/hotspot-locations/add"
    val carImageIdIntent = imageArrayList[selectedImage].carImageId
    val REQ_CODE_SPEECH_INPUT = 100
    var imageSet = false
    var imageClicked = "hotspotDetailsImageView"
    var imagesIndex = 0
    lateinit var currentPhotoPath: String
    lateinit var photoURI: Uri


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
            imagesIndex += 1
            dispatchTakePictureIntent()

        }
//        hotspotDetailsImageViewSmallTL.setOnClickListener{
//            imageClicked = "hotspotDetailsImageViewSmallTL"
//            imagesIndex += 1
//            takePictureIntent()
//        }
//        hotspotDetailsImageViewSmallTR.setOnClickListener{
//            imageClicked = "hotspotDetailsImageViewSmallTR"
//            imagesIndex += 1
//            takePictureIntent()
//        }
//        hotspotDetailsImageViewSmallBL.setOnClickListener{
//            imageClicked = "hotspotDetailsImageViewSmallBL"
//            imagesIndex += 1
//            takePictureIntent()
//        }
//        hotspotDetailsImageViewSmallBR.setOnClickListener{
//            imageClicked = "hotspotDetailsImageViewSmallBR"
//            imagesIndex += 1
//            takePictureIntent()
//        }

        finishBttnClick()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
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
                val dataRemovedArray = ArrayList<String>()
                var index = 0
                var detailImageId = 0

                if (imagesIndex >= 1) {
                    base64StringsArray.add(base64String1)
                }
                if (imagesIndex >= 2) {
                    base64StringsArray.add(base64String2)
                }
                if (imagesIndex >= 3) {
                    base64StringsArray.add(base64String3)
                }
                if (imagesIndex >= 4) {
                    base64StringsArray.add(base64String4)
                }
                if (imagesIndex == 5) {
                    base64StringsArray.add(base64String5)
                }

                    fun workload(data: String) {
                        val gson = Gson()
                        val parse = JsonParser().parse(data)
                        println("URL returned by API $parse")

                        val returnedObject = JsonParser().parse((gson.toJson(parse))).asJsonObject
                        val imageURL = returnedObject.get("imageUri").toString()
                        val dataRemoved = imageURL.replace("\"", "")
                        dataRemovedArray.add(dataRemoved)
                        index++

                        if (index == base64StringsArray.size) {
                            postData(dataRemovedArray)
                            base64StringsArray.clear()
                        }
                    }

                base64StringsArray.forEach {
                    detailImageId++
                    val ts = Timestamp(date.time)
                    val imagePOST = ImagePOST(
                        it,
                        "$ts-${selectedCar.carId}$detailImageId-TESTIMAGE.jpg"
                    )

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

                } else if (!isConnected){
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
                    progress_loader.visibility = View.INVISIBLE
                }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    println(ex)
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "com.cbsa.riley.ace.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, 1)
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            1 -> {
                val imageStream: InputStream = contentResolver.openInputStream(photoURI)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                hotspotDetailsImageView.setImageURI(photoURI)

                when (imageClicked) {
                    "hotspotDetailsImageView" -> {
                        hotspotDetailsImageView.setImageBitmap(selectedImage)
                        //hotspotDetailsImageViewSmallTL.visibility = VISIBLE
                    }
//                    "hotspotDetailsImageViewSmallTL" -> {
//                        hotspotDetailsImageViewSmallTL.setImageBitmap(imageBitmap)
//                        hotspotDetailsImageViewSmallTR.visibility = VISIBLE
//                    }
//
//                    "hotspotDetailsImageViewSmallTR" -> {
//                        hotspotDetailsImageViewSmallTR.setImageBitmap(imageBitmap)
//                        hotspotDetailsImageViewSmallBL.visibility = VISIBLE
//                    }
//                    "hotspotDetailsImageViewSmallBL" -> {
//                        hotspotDetailsImageViewSmallBL.setImageBitmap(imageBitmap)
//                        hotspotDetailsImageViewSmallBR.visibility = VISIBLE
//                    }
//                    "hotspotDetailsImageViewSmallBR" -> {
//                        hotspotDetailsImageViewSmallBR.setImageBitmap(imageBitmap)
//                    }
                }
                when (imageClicked) {
                    "hotspotDetailsImageView" -> this.base64String1 = getBase64String(selectedImage)
                    "hotspotDetailsImageViewSmallTL" -> this.base64String2 = getBase64String(selectedImage)
                    "hotspotDetailsImageViewSmallTR" -> this.base64String3 = getBase64String(selectedImage)
                    "hotspotDetailsImageViewSmallBL" -> this.base64String4 = getBase64String(selectedImage)
                    "hotspotDetailsImageViewSmallBR" -> this.base64String5 = getBase64String(selectedImage)
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

     fun postData(uri: ArrayList<String>) {
         val notes = notesText.text.toString()
         val carImageId = CarImage(carImageIdIntent)

         val xLoc = intent.getIntExtra("xLoc", 0)
         val yLoc = intent.getIntExtra("yLoc", 0)
         val title = titleText.text.toString()
         val hotspotDetails = ArrayList<HotspotDeets>()

//         println(uri)

         uri.forEach{
             val hotspotDeets = HotspotDeets(it, notes, true)
             hotspotDetails.add(hotspotDeets)
         }

         val hotspotPost =
             HotspotPost(carImageId, xLoc, yLoc, title, true, hotspotDetails)

         fun workload(data: String) {
             val gson = Gson()
             val parse = JsonParser().parse(data)
             println(parse)

             val returnedObject = JsonParser().parse((gson.toJson(parse))).asJsonObject

             val hotspotId = returnedObject.get("hotspotId").asInt
             val newXloc = returnedObject.get("xLoc").asInt
             val newYloc = returnedObject.get("yLoc").asInt
             val title = titleText.text.toString()
             val newHotspot = NewDataClassHotspot(
                 hotspotId,
                 newXloc,
                 newYloc,
                 title,
                 true,
                 carImageIdIntent,
                 hotspotDetails,
                 selectedCar.carId,
                 exterior
             )
             hotspotArrayList.add(newHotspot)

             println("returned hotspot POST data $returnedObject")
             runOnUiThread { progress_loader.visibility = View.INVISIBLE }
             sendIntent()
         }

         GlobalScope.launch {
             try {
                 URL(postURL).run {
                     openConnection().run {
                         val httpURLConnection = this as HttpURLConnection

                         httpURLConnection.requestMethod = "POST"
                         httpURLConnection.setRequestProperty("charset", "utf-8")
                         httpURLConnection.setRequestProperty("Content-Type", "application/json")

                         val gson = Gson()
                         val data = gson.toJson(hotspotPost)
                         val outputStream = DataOutputStream(httpURLConnection.outputStream)

                         if (httpURLConnection.responseCode != 200) {
                             println(httpURLConnection.responseCode)
                             println(hotspotPost)
                             println("Server Error, Please restart app")
                         }

                         outputStream.writeBytes(data)
                         println("server response code " + httpURLConnection.responseCode)
                         workload(inputStream.bufferedReader().readText())
                     }
                 }
             } catch (e: Error){
                 Toast.makeText(this@HotspotDetails, e.toString(), Toast.LENGTH_SHORT).show()
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

