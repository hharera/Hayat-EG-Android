package com.harera.dwaa.data.service

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.harera.dwaa.data.service.domain.PostMedicineRequest


interface MedicineService {

    fun getMedicines(token: String)
    fun suggestMedicine(token: String, medicineName: String)
}