package com.whiteside.dwaa.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.whiteside.dwaa.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var bind: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentCategoriesBinding.inflate(layoutInflater)

        categoriesViewModel =
            ViewModelProvider(this).get(CategoriesViewModel::class.java)


        return bind.root
    }
}