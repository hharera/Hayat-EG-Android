package com.whiteside.dwaa.utils

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.opensooq.supernova.gligar.GligarPicker

class BitmapUtils {


    companion object {

        fun convertImagePathToBitmap(data: Intent): Bitmap? {
            val imagesList = data.extras?.getStringArray(GligarPicker.IMAGES_RESULT)
            if (!imagesList.isNullOrEmpty()) {
                BitmapFactory.decodeFile(imagesList[0])?.let {
                    return it
                }
            }
            return null
        }
    }
}