package com.harera.dwaa.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindorks.example.coroutines.utils.Response
import com.harera.dwaa.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(val medicineRepository: MedicineRepository) : ViewModel() {

    private val _medicines: MutableLiveData<Response<List<FeedMedicine>>> =
        MutableLiveData<Response<List<FeedMedicine>>>()
    val medicines: LiveData<Response<List<FeedMedicine>>> = _medicines

    fun getMedicines(limit: Int, underPrice: Float) {
        _medicines.value = Response.loading(null)

        medicineRepository.getMedicines(limit, underPrice)
            .addOnSuccessListener {
                it.documents.map {
                    it.toObject(FeedMedicine::class.java)!!
                }.let {
                    _medicines.value = Response.success(it)
                }
            }
            .addOnFailureListener {
                _medicines.value = Response.error(it, null)
            }
    }
}