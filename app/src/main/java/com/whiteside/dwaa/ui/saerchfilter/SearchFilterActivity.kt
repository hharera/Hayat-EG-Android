package com.whiteside.dwaa.ui.saerchfilter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.whiteside.dwaa.R
import com.whiteside.dwaa.common.ExtrasConstants
import com.whiteside.dwaa.common.onProgressChanged
import com.whiteside.dwaa.databinding.ActivitySearchFilterBinding
import com.whiteside.dwaa.utils.time.Time
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFilterActivity : AppCompatActivity() {

    private lateinit var searchFilterViewModel: SearchFilterViewModel
    private lateinit var bind: ActivitySearchFilterBinding
    private val TAG = "SearchFilterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySearchFilterBinding.inflate(layoutInflater)
        setContentView(bind.root)

        searchFilterViewModel = ViewModelProvider(this).get(SearchFilterViewModel::class.java)

        setupObserves()
        setupListeners()
    }

    private fun setupListeners() {
        bind.addingTime.setOnClickListener {
            showAddDatePickerDialog()
        }

        bind.back.setOnClickListener {
            finish()
        }

        bind.confirm.setOnClickListener {
            searchFilterViewModel.encapsulateFilters()
        }

        bind.expireDate.setOnClickListener {
            showExpireDatePickerDialog()
        }

        bind.type.setOnClickListener {
            showMedicineTypeMenu()
        }

        bind.rangeSeekBar.onProgressChanged { start, end ->
            searchFilterViewModel.setPrice(start, end)
        }
    }

    private fun goBackWithResult() {
        val returnIntent = Intent()
        returnIntent.putExtra(
            ExtrasConstants.SEARCH_FILTERS,
            searchFilterViewModel.searchFilters.value!!
        )
        setResult(RESULT_OK, returnIntent)
        finish()
    }

    private fun showExpireDatePickerDialog() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.choose_expire_date))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            val time = Time.convertCalenderSecondsToTimestamp(it)
            searchFilterViewModel.setExpireDate(time)
        }
        datePicker.show(supportFragmentManager, TAG)
    }

    private fun setupObserves() {
        searchFilterViewModel.priceFilter.observe(this) {
            bind.chosenPrice.text = "${it.startPrice}-${it.endPrice}"
        }

        searchFilterViewModel.type.observe(this) {
            bind.type.text = it
        }

        searchFilterViewModel.addTime.observe(this) {
            bind.addingTime.text = Time.convertTimestampToString(it)
        }

        searchFilterViewModel.expireTime.observe(this) {
            bind.expireDate.text = Time.convertTimestampToString(it)
        }

        searchFilterViewModel.searchFilters.observe(this) {
            goBackWithResult()
        }
    }

    private fun showMedicineTypeMenu() {
        val popup = PopupMenu(this, bind.type)

        popup.menuInflater.inflate(R.menu.types_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            val type = it.title.toString()
            searchFilterViewModel.setMedicineType(type)
            true
        }
        popup.show()
    }

    private fun showAddDatePickerDialog() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.choose_medicine_add_date))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            val time = Time.convertCalenderSecondsToTimestamp(it)
            searchFilterViewModel.setAddTime(time)
        }
        datePicker.show(supportFragmentManager, TAG)
    }
}

