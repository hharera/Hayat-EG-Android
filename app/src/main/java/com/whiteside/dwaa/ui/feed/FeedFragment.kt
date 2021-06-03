package com.whiteside.dwaa.ui.feed

import SpaceItemDecoration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.mindorks.example.coroutines.utils.Status
import com.whiteside.dwaa.R
import com.whiteside.dwaa.common.BaseFragment
import com.whiteside.dwaa.common.onSearchConfirmed
import com.whiteside.dwaa.databinding.FragmentFeedBinding

class FeedFragment : BaseFragment() {

    private lateinit var bind: FragmentFeedBinding
    private lateinit var feedViewModel: FeedViewModel
    private var medicinesAdapter = FeedAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        feedViewModel =
            ViewModelProvider(this).get(FeedViewModel::class.java)

        bind = FragmentFeedBinding.inflate(inflater)
        bind.medicines.adapter = medicinesAdapter
        //TODO under testing
        bind.medicines.addItemDecoration(SpaceItemDecoration(requireContext(), R.dimen.item_view_space))
        bind.medicines.setHasFixedSize(true)
        bind.medicines.itemAnimator = DefaultItemAnimator()

        setupObservers()
        setupListeners()

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedViewModel.getMedicines(15, 300f)
    }

    private fun setupListeners() {
        bind.searchBar.onSearchConfirmed {
//            TODO not implemented
        }
    }

    private fun setupObservers() {
        feedViewModel.medicines.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    handleLoading()
                }
                Status.SUCCESS -> {
                    handleSuccess()
                    updateUI(it.data!!)
                }
                Status.ERROR -> {
                    handleFailure(it.error)
                }
            }
        }
    }

    private fun updateUI(medicines: List<FeedMedicine>) {
        medicinesAdapter.setList(medicines)
        medicinesAdapter.notifyDataSetChanged()
    }
}