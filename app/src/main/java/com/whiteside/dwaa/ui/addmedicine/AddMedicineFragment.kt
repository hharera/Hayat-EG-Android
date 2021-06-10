package com.whiteside.dwaa.ui.addmedicine

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.datepicker.MaterialDatePicker
import com.mindorks.example.coroutines.utils.Status
import com.opensooq.supernova.gligar.GligarPicker
import com.whiteside.dwaa.R
import com.whiteside.dwaa.common.BaseFragment
import com.whiteside.dwaa.common.afterTextChanged
import com.whiteside.dwaa.databinding.FragmentAddMedicineBinding
import com.whiteside.dwaa.common.ExtrasConstants.LOCATION_RESULT
import com.whiteside.dwaa.ui.location.ChooseLocationActivity
import com.whiteside.dwaa.utils.BitmapUtils
import com.whiteside.dwaa.utils.time.Time
import com.whiteside.dwaa.utils.location.LocationUtils
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*


@AndroidEntryPoint
class AddMedicineFragment : BaseFragment() {
    companion object {
        private val CHOOSE_LOCATION_CODE: Int = 3005
        private val IMAGE_REQ_CODE = 3004
    }

    private lateinit var locationResult: ActivityResultLauncher<Intent>
    private lateinit var addMedicineViewModel: AddMedicineViewModel
    private lateinit var bind: FragmentAddMedicineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val location = it.data!!.extras!![LOCATION_RESULT] as LatLng
                    addMedicineViewModel.setLocation(location)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentAddMedicineBinding.inflate(layoutInflater)

        addMedicineViewModel =
            ViewModelProvider(this).get(AddMedicineViewModel::class.java)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        addMedicineViewModel.image.observe(viewLifecycleOwner) {
            bind.image.setImageBitmap(it)
        }

        addMedicineViewModel.type.observe(viewLifecycleOwner) {
            bind.type.text = it
        }

        addMedicineViewModel.location.observe(viewLifecycleOwner) {
            updateLocation(it)
        }

        addMedicineViewModel.expireDate.observe(viewLifecycleOwner) {
            bind.expireDate.text = Time.convertTimestampToString(it)
        }

        addMedicineViewModel.medicineData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    handleLoading(R.string.uploading_medicine)
                }
                Status.SUCCESS -> {
                    addMedicineViewModel.uploadMedicineImage(it.data!!)
                    handleSuccess()
                }
                Status.ERROR -> {
                    onAddingFailed(it.error!!)
                }
            }
        }

        addMedicineViewModel.imageUrl.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    handleLoading(R.string.uploading_medicine)
                }
                Status.SUCCESS -> {
                    addMedicineViewModel.addMedicine(it.data!!)
                    handleSuccess()
                }
                Status.ERROR -> {
                    onAddingFailed(it.error!!)
                }
            }
        }

        addMedicineViewModel.medicineUpload.observe(viewLifecycleOwner) {
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

        addMedicineViewModel.medicineFormState.observe(viewLifecycleOwner) {
            bind.add.isEnabled = it.isValid

            if (it.nameError != null) {
                bind.name.error = getString(it.nameError)
            } else if (it.priceError != null) {
                bind.price.error = getString(it.priceError)
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
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            bind.location.text = LocationUtils.getLocationAddressName(location, geocoder)
        } catch (e: IOException) {
            handleFailure(e)
        }
    }

    private fun setupListeners() {
        bind.price.afterTextChanged {
            addMedicineViewModel.setPrice(it)
        }

        bind.name.afterTextChanged {
            addMedicineViewModel.setName(it)
        }

        bind.expireDate.setOnClickListener {
            showDatePickerDialog()
        }

        bind.type.setOnClickListener {
            showMedicineTypeMenu(it, R.menu.types_menu)
        }

        bind.image.setOnClickListener {
            onImageClicked()
        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.location.setOnClickListener {
            goToChooseLocation()
        }

        bind.add.setOnClickListener {
            //TODO update view with loading until finish
            bind.add.isEnabled = false
            addMedicineViewModel.encapsulateMedicineForm()
        }
    }

    private fun showDatePickerDialog() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.choose_expire_date))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            val time = Time.convertCalenderSecondsToTimestamp(it)
            addMedicineViewModel.setExpireDate(time)
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
            addMedicineViewModel.setMedicineType(type)
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
                addMedicineViewModel.setImage(it)
            }
        }
    }
}