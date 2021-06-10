package com.whiteside.dwaa.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiteside.dwaa.network.repository.MedicineRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val medicinesRepo : MedicineRepository): ViewModel() {

    private val _searchWord: MutableLiveData<String> = MutableLiveData()
    val searchWord: LiveData<String> = _searchWord

    fun setSearchWord(searchWord: String) {
        _searchWord.value = searchWord
    }

    fun searchProducts() {
        medicinesRepo.searchMedicines(searchWord.value!!)
    }
}