package com.cbsa.riley.ace

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.hotspotdetails.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class HotspotDetails: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotspotdetails)

        Picasso.get().load("https://via.placeholder.com/150").into(hotspotDetailsImageView)

        hotspotDetailsImageView.setOnClickListener{
            takePictureIntent()
        }

        finishBttnClick()

        notesText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (notesText.text != null) {
                    val colorValue = ContextCompat.getColor(this@HotspotDetails, android.R.color.white)
                    finishBttn.setTextColor(colorValue)
                    finishBttn.isEnabled = true
                } else {
                    val colorValue = ContextCompat.getColor(this@HotspotDetails, android.R.color.white)
                    finishBttn.setTextColor(colorValue)
                    finishBttn.isEnabled = false
                }
            }
        })
    }

    fun finishBttnClick() {
        val finishBttn: Button = finishBttn
        finishBttn.setOnClickListener {

            val intent = Intent(this, ImageViewPage::class.java)
            intent.putExtra("carMake", "Acura")
            intent.putExtra("carModel", "ILX")
            intent.putExtra("carYear", "2018")
            startActivity(intent)
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
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> {
                    val extras = data?.getExtras()
                    val imageBitmap = extras?.get("data") as Bitmap


                    hotspotDetailsImageView.setImageBitmap(imageBitmap)

                    //convertImageFileToBase64()
                    val colorValue = ContextCompat.getColor(this, android.R.color.white)
                    finishBttn.setTextColor(colorValue)
                    finishBttn.isEnabled = true

                }
            }
        }

        var mCurrentPhotoPath: String

        fun createImageFile(): File {
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).apply {
                // Save a file: path for use with ACTION_VIEW intents
                mCurrentPhotoPath = absolutePath
            }
        }
    }



//    fun convertImageFileToBase64() {
//
//        return FileInputStream(imageFile).use { inputStream ->
//            ByteArrayOutputStream().use { outputStream ->
//                Base64OutputStream(outputStream, Base64.DEFAULT).use { base64FilterStream ->
//                    inputStream.copyTo(base64FilterStream)
//                    base64FilterStream.flush()
//                    outputStream.toString()
//                }
//            }
//        }
//
////        val byteArrayOutputStream = ByteArrayOutputStream()
////        Bitmap.CompressFormat(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
////        val byteArray = byteArrayOutputStream.toByteArray()
//    }

}
