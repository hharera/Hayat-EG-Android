package com.harera.hayat.common

import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harera.hayat.R
import com.mancj.materialsearchbar.MaterialSearchBar
import com.harera.hayat.custom.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
    val loadingDialog: LoadingDialog by lazy { LoadingDialog(this) }

    fun handleFailure(e: Exception) {
        dismissLoading()
        showErrorToast()
        printException(e)
    }

    fun handleLoading(messageId: Int) {
        showLoading(messageId)
    }

    fun handleSuccess() {
        dismissLoading()
    }

    private fun showErrorToast() {
        Toast.makeText(this, resources.getText(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }

    private fun printException(e: Exception) {
        e.printStackTrace()
    }

    private fun showLoading(messageId: Int) {
        loadingDialog.show(messageId)
    }

    private fun dismissLoading() {
        loadingDialog.dismiss()
    }

    fun hideSoftKeyboard() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.setOnNextClicked(onNextClicked: () -> Unit) {
    this.setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            onNextClicked()
        }
        true
    }
}

fun EditText.setOnDoneClicked(onDoneClicked: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onDoneClicked()
        }
        true
    }
}

fun MaterialSearchBar.onSearchConfirmed(afterTextChanged: (String) -> Unit) {
    this.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
        override fun onSearchStateChanged(enabled: Boolean) {
        }

        override fun onSearchConfirmed(text: CharSequence) {
            afterTextChanged.invoke(text.toString())
        }

        override fun onButtonClicked(buttonCode: Int) {
        }
    })
}
