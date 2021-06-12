package com.whiteside.dwaa.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.mindorks.example.coroutines.utils.Response
import com.whiteside.dwaa.network.repository.MedicineRepository
import com.whiteside.dwaa.ui.feed.FeedMedicine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val medicinesRepo: MedicineRepository) :
    ViewModel() {

    private val _searchWord: MutableLiveData<String> = MutableLiveData()
    val searchWord: LiveData<String> = _searchWord

    private val _searchResult: MutableLiveData<Response<List<FeedMedicine>>> = MutableLiveData()
    val searchResult: LiveData<Response<List<FeedMedicine>>> = _searchResult

    fun setSearchWord(searchWord: String) {
        _searchWord.value = searchWord
    }

    fun defaultSearch() {
        searchMedicines(null, null)
    }

    //TODO not working
    fun searchMedicinesWithSort(name: String, direction: Query.Direction) {
        searchMedicines(name, direction)
    }

    private fun searchMedicines(propertyName: String?, direction: Query.Direction?) {
        _searchResult.value = Response.loading(null)
        medicinesRepo.searchMedicines(searchWord.value!!, 20, propertyName, direction)
            .addOnSuccessListener {
                it.documents.map {
                    it.toObject(FeedMedicine::class.java)!!
                }.let {
                    _searchResult.value = Response.success(it)
                }
            }
            .addOnFailureListener {
                _searchResult.value = Response.error(it, null)
            }
    }
}