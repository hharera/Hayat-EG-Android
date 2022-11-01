package com.harera.hayat.feature.postingdonation

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.datepicker.MaterialDatePicker
import com.harera.hayat.R
import com.harera.hayat.common.BaseFragment
import com.harera.hayat.common.ExtrasConstants.LOCATION_RESULT
import com.harera.hayat.common.afterTextChanged
import com.harera.hayat.databinding.FragmentDonationBinding
import com.harera.hayat.ui.location.ChooseLocationActivity
import com.harera.hayat.utils.BitmapUtils
import com.harera.hayat.utils.location.LocationUtils
import com.harera.hayat.utils.time.Time
import com.mindorks.example.coroutines.utils.Status
import com.opensooq.supernova.gligar.GligarPicker
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*


@AndroidEntryPoint
class DonationFragment : BaseFragment() {
    companion object {
        private val CHOOSE_LOCATION_CODE: Int = 3005
        private val IMAGE_REQ_CODE = 3004
    }

    private lateinit var locationResult: ActivityResultLauncher<Intent>
    private lateinit var donationViewModel: DonationViewModel
    private lateinit var bind: FragmentDonationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val location = it.data!!.extras!![LOCATION_RESULT] as LatLng
                    donationViewModel.setLocation(location)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentDonationBinding.inflate(layoutInflater)

        donationViewModel =
            ViewModelProvider(this).get(DonationViewModel::class.java)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        donationViewModel.image.observe(viewLifecycleOwner) {
            bind.image.setImageBitmap(it)
        }

        donationViewModel.location.observe(viewLifecycleOwner) {
            updateLocation(it)
        }

        donationViewModel.expireDate.observe(viewLifecycleOwner) {
            bind.expireDate.text = Time.convertTimestampToString(it)
        }

        donationViewModel.postMedicineRequest.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    handleLoading(R.string.uploading_medicine)
                }
                Status.SUCCESS -> {
                    donationViewModel.uploadMedicineImage(it.data!!)
                    handleSuccess()
                }
                Status.ERROR -> {
                    onAddingFailed(it.error!!)
                }
            }
        }

        donationViewModel.imageUrl.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    handleLoading(R.string.uploading_medicine)
                }
                Status.SUCCESS -> {
                    donationViewModel.addMedicine(it.data!!)
                    handleSuccess()
                }
                Status.ERROR -> {
                    onAddingFailed(it.error!!)
                }
            }
        }

        donationViewModel.medicineUpload.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    handleLoading(R.string.uploading_medicine)
                }
                Status.SUCCESS -> {
                    onAddingSuccess()
                }
                Status.ERROR -> {
                    onAddingFailed(it.error!!)
                }
            }
        }

        donationViewModel.medicineFormState.observe(viewLifecycleOwner) {
            bind.add.isEnabled = it.isValid

            if (it.nameError != null) {
                bind.name.error = getString(it.nameError)
            } else if (it.expireDateError != null) {
                showToast(it.expireDateError)
            } else if (it.typeError != null) {
                showToast(it.typeError)
            } else if (it.locationError != null) {
                showToast(it.locationError)
            } else if (it.imageError != null) {
                showToast(it.imageError)
            }
        }
    }

    private fun onAddingFailed(exception: Exception) {
        handleFailure(exception)
        bind.add.isEnabled = true
    }

    private fun onAddingSuccess() {
        handleSuccess()
        bind.add.isEnabled = true
    }

    private fun updateLocation(location: LatLng) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            bind.location.text = LocationUtils.getLocationAddressName(location, geocoder)
        } catch (e: IOException) {
            handleFailure(e)
        }
    }

    private fun setupListeners() {
        bind.name.afterTextChanged {
            donationViewModel.setName(it)
        }

        bind.amount.afterTextChanged {
            donationViewModel.setAmount(it)
        }

        bind.expireDate.setOnClickListener {
            showDatePickerDialog()
        }

        bind.image.setOnClickListener {
            onImageClicked()
        }

        bind.back.setOnClickListener {
            activity?.onBackPressed()
        }

        bind.location.setOnClickListener {
            goToChooseLocation()
        }

        bind.unit.setOnClickListener {
            createUnitsMenu(it)
        }

        bind.add.setOnClickListener {
            //TODO update view with loading until finish
            bind.add.isEnabled = false
            donationViewModel.encapsulateMedicineForm()
        }
    }

    private fun createUnitsMenu(it: View) {
    }

    private fun showDatePickerDialog() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.choose_expire_date))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            val time = Time.convertCalenderSecondsToTimestamp(it)
            donationViewModel.setExpireDate(time)
        }
        datePicker.show(childFragmentManager, "EXPIRE DATE")
    }

    private fun goToChooseLocation() {
        val intent = Intent(context, ChooseLocationActivity::class.java)
        locationResult.launch(intent)
    }

    private fun showMedicineTypeMenu(view: View, menu: Int) {
        val popup = PopupMenu(context, view)

        popup.menuInflater.inflate(menu, popup.menu)
        popup.setOnMenuItemClickListener {
            val type = it.title.toString()
//            donationViewModel.setMedicineType(type)
            true
        }
        popup.show()
    }

    private fun showUnitsMenu(view: View, menu: Int) {
        val popup = PopupMenu(context, view)

        popup.menuInflater.inflate(menu, popup.menu)
        popup.setOnMenuItemClickListener {
            val unit = it.title.toString()
            donationViewModel.setUnit(unit)
            true
        }
        popup.show()
    }

    private fun onImageClicked() {
        GligarPicker()
            .requestCode(IMAGE_REQ_CODE)
            .limit(1)
            .withFragment(this)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQ_CODE) {
            val imageBitmap = BitmapUtils.convertImagePathToBitmap(data)
            imageBitmap?.let {
                donationViewModel.setImage(it)
            }
        }
    }
}