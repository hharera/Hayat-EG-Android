package com.harera.dwaa.data.service

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.harera.dwaa.data.repository.MedicineRepository
import com.harera.dwaa.data.service.domain.PostMedicineRequest
import io.ktor.client.*
import javax.inject.Inject


class MedicineServiceImpl @Inject constructor(
    private val client: HttpClient,
) : MedicineService {

    override fun getMedicines(token: String) {
        TODO("Not yet implemented")
    }

    override fun suggestMedicine(token: String, medicineName: String) {
        TODO("Not yet implemented")
    }
}