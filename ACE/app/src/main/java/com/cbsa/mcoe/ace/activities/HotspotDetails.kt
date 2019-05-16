package com.cbsa.mcoe.ace.activities

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
import android.widget.Button
import android.widget.Toast
import com.cbsa.mcoe.ace.R
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
import java.util.*
import kotlin.collections.ArrayList

class HotspotDetails: AppCompatActivity(){

    private var base64String: String = ""
    private val imageURL = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/images/add"
    private val postURL = "https://mcoe-webapp-projectdeltaace.azurewebsites.net/deltaace/v1/hotspot-locations/add"
    private val carImageIdIntent = imageArrayList[selectedImage].carImageId
    private val REQCODESPEECHINPUT = 100
    var imageSet = false
    private lateinit var currentPhotoPath: String
    private lateinit var photoURI: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotspotdetails)
        setClickListeners()
    }

    //creates the image file for the dispatchTakePictureIntent() function
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = getTimeStamp().toString()
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    //Sets all the onCLickListeners and the TextWatcher
    private fun setClickListeners() {
        floatingActionButton.setOnClickListener { startVoiceInput() }
        hotspotDetailsImageView.setOnClickListener{ dispatchTakePictureIntent() }
        finishBttn.setOnClickListener { postImage() }
        titleText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { if (imageSet && titleText.text.isNotEmpty()){ enableButton(finishBttn) } else { disableButton(finishBttn) } }
            override fun afterTextChanged(s: Editable) {}
        })
    }

    //Checks for an internet connection and posts the picture to the data, gets the uri back and passes it to the postData() function
    private fun postImage(){
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        val ts = getTimeStamp()
        val imagePOST = ImagePOST(base64String, "TimeStamp:$ts--CarId:${selectedCar.carId}--TESTIMAGE.jpg")

        if (isConnected) {
            finishBttn.isEnabled = false
            runOnUiThread { progress_loader.visibility = View.VISIBLE }

            fun workload(data: String) {
                val gson = Gson()
                val parse = JsonParser().parse(data)
                val returnedObject = JsonParser().parse((gson.toJson(parse))).asJsonObject
                val imageURL = returnedObject.get("imageUri").toString()
                val dataRemoved = imageURL.replace("\"", "")
                println("URL returned by API $parse")

                postHotspotData(dataRemoved)
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
        } else if (!isConnected) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            progress_loader.visibility = View.INVISIBLE
        }
    }

    //Creates an image file and calls the takePicture intent to start the camera
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
                        "com.cbsa.mcoe.ace.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, 1)
                }
            }
        }
    }

    //called after picture is taken and voice input stops.
    //adds the picture to the thumbnail and calls the getBase64string() function
    //checks which field is empty and fills it with voiceinput text
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            1 -> {
                val imageStream: InputStream? = contentResolver.openInputStream(photoURI)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                hotspotDetailsImageView.setImageBitmap(selectedImage)
                this.base64String = getBase64String(selectedImage)

                imageSet = true
                if (titleText.text.toString() != ""){
                    val colorValue = ContextCompat.getColor(this, android.R.color.white)
                    finishBttn.setTextColor(colorValue)
                    finishBttn.isEnabled = true
                }
            }
        }
        when (requestCode) {
            REQCODESPEECHINPUT -> {
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

    //takes all the hotspot info and posts it to the database, then calls sendIntent()
    private fun postHotspotData(uri: String) {
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

             val newHotspot = NewDataClassHotspot(hotspotId, newXloc, newYloc, title, true, carImageIdIntent, hotspotDetails, selectedCar.carId, exterior)
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
                         outputStream.writeBytes(data)
                         println("server response code " + httpURLConnection.responseCode)

                         if (httpURLConnection.responseCode != 200) {
                             println(httpURLConnection.responseCode)
                             println(hotspotPost)
                             println("Server Error, Please restart app")
                         }

                         workload(inputStream.bufferedReader().readText())
                     }
                 }
             } catch (e: Error){
                 println(e)
             }
         }
    }

    //starts the ImageViewPage activity
    private fun sendIntent(){
        val intent = Intent(this, ImageViewPage::class.java)
        startActivity(intent)
    }

    //deals with voice input until onActivityResult() function
    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start talking")
        try {
            startActivityForResult(intent, REQCODESPEECHINPUT)
        } catch (a: ActivityNotFoundException) {

        }
    }

    //generates and returns a timestamp
    private fun getTimeStamp():Timestamp {
        val date = Date()
        return Timestamp(date.time)
    }

    //enables button
    private fun enableButton(button: Button) {
        button.isEnabled = true
        val white = ContextCompat.getColor(this, R.color.white)
        button.setTextColor(white)
    }

    //disables button
    private fun disableButton(button: Button) {
        button.isEnabled = false
        val gray = ContextCompat.getColor(this, R.color.disabledButton)
        button.setTextColor(gray)
    }

    //takes a bitmap and returns a base64 string
    private fun getBase64String(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP)
    }
}

