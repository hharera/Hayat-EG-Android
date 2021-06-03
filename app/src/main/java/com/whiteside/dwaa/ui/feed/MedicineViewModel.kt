package com.whiteside.dwaa.ui.feed

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MedicineViewModel : ViewModel() {

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    fun loadImage(imageUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            Picasso.get().load(imageUrl).get().let {
                withContext(Dispatchers.Main) {
                    _image.value = it
                }
            }
        }
    }
}