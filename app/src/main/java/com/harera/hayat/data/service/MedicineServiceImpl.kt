package com.harera.hayat.data.service

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