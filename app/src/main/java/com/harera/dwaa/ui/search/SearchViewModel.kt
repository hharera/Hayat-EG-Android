package com.harera.dwaa.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.mindorks.example.coroutines.utils.Response
import com.harera.dwaa.data.repository.MedicineRepository
import com.harera.dwaa.ui.feed.FeedMedicine
import com.harera.dwaa.ui.saerchfilter.SearchFilters
import com.harera.dwaa.ui.saerchfilter.SearchSorting
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val medicinesRepo: MedicineRepository) :
    ViewModel() {

    private val _searchWord: MutableLiveData<String> = MutableLiveData()
    val searchWord: LiveData<String> = _searchWord

    private val _searchResult: MutableLiveData<Response<List<FeedMedicine>>> = MutableLiveData()
    val searchResult: LiveData<Response<List<FeedMedicine>>> = _searchResult

    private val _searchFilters: MutableLiveData<SearchFilters> = MutableLiveData()
    val searchFilters: LiveData<SearchFilters> = _searchFilters

    private val _searchSorting: MutableLiveData<SearchSorting> = MutableLiveData()
    val searchSorting: LiveData<SearchSorting> = _searchSorting

    fun setSearchWord(searchWord: String) {
        _searchWord.value = searchWord
    }

    fun setFilterPreferences(filter: SearchFilters) {
        _searchFilters.value = filter
    }

    fun setSortPreferences(name: String, ascending: Query.Direction) {
        _searchSorting.value = SearchSorting(name, ascending)
    }

//    fun searchMedicines() {
//        _searchResult.value = Response.loading(null)
//        medicinesRepo.apply {
//            if (searchSorting.value != null && searchFilters.value != null)
//                searchMedicines(
//                    searchWord.value!!,
//                    20,
//                    searchSorting.value!!.propertyName,
//                    searchFilters.value!!.,
//                ).let {
//                    handleTask(it)
//                }
//            else if (searchSorting.value != null)
//                searchMedicines(
//                    searchWord.value!!,
//                    20,
//                    searchSorting.value!!,
//                ).let {
//                    handleTask(it)
//                }
//            else if (searchFilters.value != null)
//                searchMedicines(
//                    searchWord.value!!,
//                    20,
//                    searchFilters.value!!,
//                ).let {
//                    handleTask(it)
//                }
//            else
//                searchMedicines(
//                    searchWord.value!!,
//                    20,
//                ).let {
//                    handleTask(it)
//                }
//        }
//    }

    private fun handleTask(task: Task<QuerySnapshot>) {
        task.addOnSuccessListener {
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