package com.whiteside.dwaa.ui.feed

import SpaceItemDecoration
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.mindorks.example.coroutines.utils.Status
import com.whiteside.dwaa.R
import com.whiteside.dwaa.common.BaseFragment
import com.whiteside.dwaa.common.ConnectionLiveData
import com.whiteside.dwaa.common.onSearchConfirmed
import com.whiteside.dwaa.databinding.FragmentFeedBinding
import com.whiteside.dwaa.ui.connection.NoInternetConnection
import com.whiteside.dwaa.utils.Connectivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : BaseFragment() {

    @Inject
    lateinit var connectivity: Connectivity

    private lateinit var connectionLiveData: ConnectionLiveData

    private lateinit var bind: FragmentFeedBinding
    private lateinit var feedViewModel: FeedViewModel
    private var medicinesAdapter = FeedAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        bind = FragmentFeedBinding.inflate(inflater)

        connectionLiveData = ConnectionLiveData(requireContext())

        bind.medicines.adapter = medicinesAdapter
        bind.medicines.setHasFixedSize(true)

        setupObservers()
        setupListeners()

        return bind.root
    }

    private fun goInternetActivity() {
        val intent = Intent(context, NoInternetConnection::class.java)
        startActivity(intent)
    }

    private fun setupListeners() {
        bind.searchBar.onSearchConfirmed {
//            TODO not implemented
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (connectivity.isConnected()) {
            feedViewModel.getMedicines(15, 300f)
        } else {
            goInternetActivity()
        }
    }

    private fun setupObservers() {
        feedViewModel.medicines.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    bind.shimmerFrame.startShimmer()
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
        connectionLiveData.observe(viewLifecycleOwner) {
            updateSnackBarUI(it)
        }
    }

    private fun updateSnackBarUI(isConnected: Boolean) {
        if (isConnected) {
            Snackbar.make(bind.medicines, getString(R.string.back_connected), Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.app_blue))
                .show()
        } else {
            Snackbar.make(bind.medicines, getString(R.string.went_disconnected), Snackbar.LENGTH_LONG)
                .setBackgroundTint(resources.getColor(R.color.app_blue))
                .show()
        }
    }

    private fun updateUI(medicines: List<FeedMedicine>) {
        bind.shimmerFrame.stopShimmer()
        bind.shimmerFrame.visibility = View.GONE
        bind.medicines.visibility = View.VISIBLE
        medicinesAdapter.setList(medicines)
        medicinesAdapter.notifyDataSetChanged()
    }
}