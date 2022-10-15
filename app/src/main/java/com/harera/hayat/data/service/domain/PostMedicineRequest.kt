package com.harera.hayat.data.service.domain

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class PostMedicineRequest(
    val name: String,
    val id: String,
    val uid: String,
    val expireDate: Timestamp,
    val addingTime: Timestamp,
    val price: Float,
    val location: LatLng,
    val category: String,
    @get:Exclude @set:Exclude var image: Bitmap,
    var imageUrl: String? = null,
)