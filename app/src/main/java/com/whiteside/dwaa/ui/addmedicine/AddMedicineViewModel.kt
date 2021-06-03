package com.whiteside.dwaa.ui.addmedicine

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.mindorks.example.coroutines.utils.Response
import com.whiteside.dwaa.R
import com.whiteside.dwaa.model.MedicineData
import com.whiteside.dwaa.network.repository.AuthManager
import com.whiteside.dwaa.network.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddMedicineViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val medicineRepository: MedicineRepository
) : ViewModel() {

    private val _price: MutableLiveData<String> = MutableLiveData()
    val price: LiveData<String> = _price

    private val _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String> = _name

    private val _expireDate: MutableLiveData<Timestamp> = MutableLiveData()
    val expireDate: LiveData<Timestamp> = _expireDate

    private val _type: MutableLiveData<String> = MutableLiveData()
    val type: LiveData<String> = _type

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    private val _location: MutableLiveData<LatLng> = MutableLiveData()
    val location: LiveData<LatLng> = _location

    private val _imageUrl: MutableLiveData<Response<String>> = MutableLiveData()
    val imageUrl: LiveData<Response<String>> = _imageUrl

//    //TODO observe
//    private val _error: MutableLiveData<Exception> = MutableLiveData()
//    val error: LiveData<Exception> = _error
//
//    //TODO observe
//    private val _loading: MutableLiveData<Any?> = MutableLiveData()
//    val loading: LiveData<Any?> = _loading

    private val _medicineFormState: MutableLiveData<AddMedicineFormState> =
        MutableLiveData(AddMedicineFormState())
    val medicineFormState: LiveData<AddMedicineFormState> = _medicineFormState

    private val _medicineData: MutableLiveData<Response<MedicineData>> = MutableLiveData()
    val medicineData: LiveData<Response<MedicineData>> = _medicineData

    private val _medicineUpload: MutableLiveData<Response<Any>> = MutableLiveData()
    val medicineUpload: LiveData<Response<Any>> = _medicineUpload

    fun setPrice(price: String) {
        _price.value = price
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

    fun setMedicineType(type: String) {
        _type.value = type
        checkFormValidity()
    }

    fun setLocation(location: LatLng) {
        _location.value = location
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (_name.value == null || _name.value!!.isBlank()) {
            _medicineFormState.value = AddMedicineFormState(nameError = R.string.empty_name_error)
        } else if (_price.value == null || _price.value!!.isBlank()) {
            _medicineFormState.value = AddMedicineFormState(priceError = R.string.empty_price_state)
        } else if (_expireDate.value == null) {
            _medicineFormState.value =
                AddMedicineFormState(expireDateError = R.string.empty_exprie_date_error)
        } else if (_type.value == null) {
            _medicineFormState.value = AddMedicineFormState(typeError = R.string.empty_type_state)
        } else if (_location.value == null) {
            _medicineFormState.value =
                AddMedicineFormState(typeError = R.string.empty_location_error)
        } else if (_image.value == null) {
            _medicineFormState.value = AddMedicineFormState(typeError = R.string.empty_image_error)
        } else {
            _medicineFormState.value = AddMedicineFormState(isValid = true)
        }
    }

    fun addMedicine(url: String) {
        _medicineUpload.value = Response.loading(null)
        medicineRepository.addMedicine(
            medicineData.value!!.data!!.apply {
                imageUrl = url
            })
            .addOnSuccessListener {
                _medicineUpload.value = Response.success(null)
            }
            .addOnFailureListener {
                _medicineUpload.value = Response.error(it, null)
            }
    }

    fun uploadMedicineImage(medicineData: MedicineData) {
        _imageUrl.value = Response.loading(null)
        medicineRepository.uploadMedicineImage(
            medicineData.image,
            medicineData.category,
            medicineData.id
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
        _medicineData.value = Response.loading(null)

        val medicineId = medicineRepository.getNewMedicineId(type.value!!)
        _medicineData.value = Response.success(
            MedicineData(
                name = name.value!!,
                expireDate = expireDate.value!!,
                addingTime = Timestamp.now(),
                price = price.value!!.toFloat(),
                uid = authManager.getCurrentUser()!!.uid,
                location = location.value!!,
                category = type.value!!,
                image = image.value!!,
                id = medicineId
            )
        )
    }
}