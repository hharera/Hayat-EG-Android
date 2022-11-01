package com.harera.hayat.data.service


interface MedicineService {

    fun getMedicines(token: String)
    fun suggestMedicine(token: String, medicineName: String)
}