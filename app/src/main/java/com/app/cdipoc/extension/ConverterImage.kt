package com.app.cdipoc.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Base64OutputStream
import android.util.Log
import com.app.cdipoc.R
import java.io.*


class ConverterImage {
    fun convertToBase64(context: Context, imageUri: Uri): String {
        val imageStream = context.contentResolver.openInputStream(imageUri)
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        return encodeImage(selectedImage)
    }

    fun convertToBase64V2(context: Context, imageUri: Uri): String {
        val selectPhoto = imageUri
        try {
            var bitmap: Bitmap? = null
            selectPhoto?.let {
                if (Build.VERSION.SDK_INT <  Build.VERSION_CODES.P) {
                    bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                    bitmap = ImageDecoder.decodeBitmap(source)
                }
            }

            return encodeImage(bitmap).toString()

        } catch (e: IOException) {
            Log.d("ConverterImage", "convertToBase64V2: " + e.message)
        }

        return ""
    }

    fun encodeImage(bitmap: Bitmap?): String {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 10, stream)
        val bytes = stream.toByteArray()
        return encodeToString(bytes, Base64.DEFAULT)
    }

    fun convertToBase64v3(context: Context): String {
        val baos = ByteArrayOutputStream()
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.test)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes: ByteArray = baos.toByteArray()
        return encodeToString(imageBytes, Base64.DEFAULT)
    }
}