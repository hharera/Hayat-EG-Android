package com.whiteside.dwaa.ui.search

import android.os.Bundle
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.Query
import com.mindorks.example.coroutines.utils.Status
import com.whiteside.dwaa.R
import com.whiteside.dwaa.adapter.MedicinesAdapter
import com.whiteside.dwaa.common.BaseActivity
import com.whiteside.dwaa.databinding.ActivitySearchBinding
import com.whiteside.dwaa.common.ExtrasConstants
import com.whiteside.dwaa.common.onSearchConfirmed
import com.whiteside.dwaa.ui.feed.FeedMedicine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    private lateinit var bind: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private var medicinesAdapter = MedicinesAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        bind = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.medicines.adapter = medicinesAdapter

        intent.extras!!.let {
            val searchWord = it.getString(ExtrasConstants.searchWord)!!
            viewModel.setSearchWord(searchWord)
        }

        setupObservers()
        setupListeners()
        viewModel.defaultSearch()
    }

    private fun setupListeners() {
        bind.sort.setOnClickListener {
            showSortMenu()
        }

        bind.filter.setOnClickListener {
            //TODO not implemented
        }

        bind.searchBar.onSearchConfirmed {
            viewModel.setSearchWord(it)
            viewModel.defaultSearch()
        }
    }

    private fun setupObservers() {
        viewModel.searchWord.observe(this) {
            bind.searchBar.text = (it)
        }

        viewModel.searchResult.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    handleSuccess()
                    medicinesAdapter.setList(it.data!!)
                    medicinesAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    handleFailure(it.error!!)
                }
                Status.LOADING -> {
                    handleLoading(R.string.loading)
                }
            }
        }
    }

    private fun showSortMenu() {
        val popup = PopupMenu(this, bind.sortIcon)

        popup.menuInflater.inflate(R.menu.sort_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            val typeId = it.itemId
            updateSearch(typeId)
            true
        }
        popup.show()
    }

    private fun updateSearch(typeId: Int) {
        when (typeId) {
            R.id.price_low_to_high -> {
                viewModel.searchMedicinesWithSort(FeedMedicine::price.name, Query.Direction.ASCENDING)
            }
            R.id.price_high_to_low -> {
                viewModel.searchMedicinesWithSort(FeedMedicine::price.name, Query.Direction.DESCENDING)
            }
        }
    }
}