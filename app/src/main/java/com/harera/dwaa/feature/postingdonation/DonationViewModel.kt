package com.harera.dwaa.feature.postingdonation

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.mindorks.example.coroutines.utils.Response
import com.harera.dwaa.R
import com.harera.dwaa.data.domain.Unit
import com.harera.dwaa.data.service.domain.PostMedicineRequest
import com.harera.dwaa.network.repository.AuthManager
import com.harera.dwaa.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val medicineRepository: MedicineRepository
) : ViewModel() {

    private val _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String> = _name

    private val _expireDate: MutableLiveData<Timestamp> = MutableLiveData()
    val expireDate: LiveData<Timestamp> = _expireDate

    private val _amount: MutableLiveData<String> = MutableLiveData()
    val amount: LiveData<String> = _amount

    private val _unit: MutableLiveData<String> = MutableLiveData()
    val unit: LiveData<String> = _unit

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    private val _location: MutableLiveData<LatLng> = MutableLiveData()
    val location: LiveData<LatLng> = _location

    private val _imageUrl: MutableLiveData<Response<String>> = MutableLiveData()
    val imageUrl: LiveData<Response<String>> = _imageUrl

    private val _medicineFormState: MutableLiveData<DonationFormState> =
        MutableLiveData(DonationFormState())
    val medicineFormState: LiveData<DonationFormState> = _medicineFormState

    private val _Post_medicineRequest: MutableLiveData<Response<PostMedicineRequest>> = MutableLiveData()
    val postMedicineRequest: LiveData<Response<PostMedicineRequest>> = _Post_medicineRequest

    private val _medicineUpload: MutableLiveData<Response<Any>> = MutableLiveData()
    val medicineUpload: LiveData<Response<Any>> = _medicineUpload

    private val _unitList: MutableLiveData<List<Unit>> = MutableLiveData()
    val unitList: LiveData<List<Unit>> = _unitList

    fun setUnit(unit: String) {
        _unit.value = unit
        checkFormValidity()
    }

    fun setName(name: String) {
        _name.value = name
        checkFormValidity()
    }

    fun setExpireDate(expireDate: Timestamp) {
        _expireDate.value = expireDate
        checkFormValidity()
    }

    fun setImage(imageBitmap: Bitmap) {
        _image.value = imageBitmap
        checkFormValidity()
    }

    fun setAmount(it: String) {
        _amount.value = it
        checkFormValidity()
    }

    fun setLocation(location: LatLng) {
        _location.value = location
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (_name.value == null || _name.value!!.isBlank()) {
            _medicineFormState.value = DonationFormState(nameError = R.string.empty_name_error)
        } else if (_expireDate.value == null) {
            _medicineFormState.value =
                DonationFormState(expireDateError = R.string.empty_exprie_date_error)
        } else if (_location.value == null) {
            _medicineFormState.value =
                DonationFormState(typeError = R.string.empty_location_error)
        } else if (_image.value == null) {
            _medicineFormState.value = DonationFormState(typeError = R.string.empty_image_error)
        } else {
            _medicineFormState.value = DonationFormState(isValid = true)
        }
    }

    fun addMedicine(url: String) {
        _medicineUpload.value = Response.loading(null)
        medicineRepository.insertDonationPost(
            postMedicineRequest.value!!.data!!.apply {
                imageUrl = url
            })
            .addOnSuccessListener {
                _medicineUpload.value = Response.success(null)
            }
            .addOnFailureListener {
                _medicineUpload.value = Response.error(it, null)
            }
    }

    fun uploadMedicineImage(postMedicineRequest: PostMedicineRequest) {
        _imageUrl.value = Response.loading(null)
        medicineRepository.uploadMedicineImage(
            postMedicineRequest.image,
            postMedicineRequest.category,
            postMedicineRequest.id
        )
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {
                    _imageUrl.value = Response.success(it.toString())
                }
            }.addOnFailureListener {
                _imageUrl.value = Response.error(it, null)
            }
    }

    fun encapsulateMedicineForm() {
        _Post_medicineRequest.value = Response.loading(null)

//        val medicineId = medicineRepository.getNewMedicineId(type.value!!)
//        _Post_medicineRequest.value = Response.success(
//            PostMedicineRequest(
//                name = name.value!!,
//                expireDate = expireDate.value!!,
//                addingTime = Timestamp.now(),
//                price = price.value!!.toFloat(),
//                uid = authManager.getCurrentUser()!!.uid,
//                location = location.value!!,
//                category = type.value!!,
//                image = image.value!!,
//                id = medicineId
//            )
//        )
    }
}