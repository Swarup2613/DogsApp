package com.example.dogsapp.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.io.File
import java.io.OutputStream
import java.util.Objects

object Helper {


    fun shareImage(
        bitmap: Bitmap, context: Context, contentResolver: ContentResolver
    ) {
        try {
            val path = MediaStore.Images.Media.insertImage(
                contentResolver, bitmap, "image Description", null
            )
            val uri = Uri.parse(path)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "powered by Dog API app")
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
        } catch (e: Exception) {
            showToast(context, e.message.toString())

        }


    }


    fun saveImageToGallery(
        bitmap: Bitmap, context: Context, contentResolver: ContentResolver
    ) {
        val fos: OutputStream
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + ".jpg")
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                contentValues.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + "DogImages"
                )
                val imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                Objects.requireNonNull<OutputStream?>(fos)
                showToast(context, "Imaged saved in the gallery")


            }

        } catch (e: Exception) {
            showToast(context, "Image not saved")
        }

    }


    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }


    fun setImageWithGlide(context: Context, dogImageUrl: String, iv_selected_dog: ImageView) {
        Glide.with(context).load(dogImageUrl).into(iv_selected_dog)

    }


}