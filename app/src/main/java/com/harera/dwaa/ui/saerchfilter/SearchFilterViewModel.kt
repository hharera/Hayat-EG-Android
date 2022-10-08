package com.harera.dwaa.ui.saerchfilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.harera.dwaa.ui.saerchfilter.model.PriceFilter

class SearchFilterViewModel : ViewModel() {

    private val _filters: MutableLiveData<SearchFilters> =
        MutableLiveData<SearchFilters>()
    val searchFilters: LiveData<SearchFilters> = _filters

    private val _priceFilter: MutableLiveData<PriceFilter> =
        MutableLiveData<PriceFilter>()
    val priceFilter: LiveData<PriceFilter> = _priceFilter

    private val _addTime: MutableLiveData<Timestamp> =
        MutableLiveData<Timestamp>()
    val addTime: LiveData<Timestamp> = _addTime

    private val _expireTime: MutableLiveData<Timestamp> =
        MutableLiveData<Timestamp>()
    val expireTime: LiveData<Timestamp> = _expireTime

    private val _type: MutableLiveData<String> = MutableLiveData()
    val type: LiveData<String> = _type

    fun setPrice(start: Int, end: Int) {
        _priceFilter.value = (PriceFilter(start, end))
    }

    fun setMedicineType(type: String) {
        _type.value = type
    }

    fun setExpireDate(time: Timestamp) {
        _expireTime.value = time
    }

    fun setAddTime(time: Timestamp) {
        _addTime.value = time
    }

    fun encapsulateFilters() {
        _filters.value = SearchFilters(
            priceFilter = priceFilter.value,
            category = type.value,
            addingTime = addTime.value,
            expireDate = expireTime.value,
        )
    }
}