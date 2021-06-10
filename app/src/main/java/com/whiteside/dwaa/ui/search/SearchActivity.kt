package com.whiteside.dwaa.ui.search

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.whiteside.dwaa.common.BaseActivity
import com.whiteside.dwaa.databinding.ActivitySearchBinding
import com.whiteside.dwaa.common.ExtrasConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    private lateinit var bind: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        bind = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(bind.root)

        intent.extras!!.let{
            val searchWord = it.getString(ExtrasConstants.searchWord)!!
            viewModel.setSearchWord(searchWord)
        }

        viewModel.searchProducts()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.searchWord.observe(this) {
            bind.searchBar.text = (it)
        }
    }
}