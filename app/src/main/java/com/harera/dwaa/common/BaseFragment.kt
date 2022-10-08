package com.harera.dwaa.common

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kinda.alert.KAlertDialog
import com.harera.dwaa.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseFragment : Fragment() {
    private val dialog: KAlertDialog by lazy {
        KAlertDialog(context, KAlertDialog.PROGRESS_TYPE).apply {
            progressHelper.barColor = R.color.app_yellow
            titleText = getString(R.string.loading)
            setCancelable(false)
        }
    }

    fun showToast(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun handleFailure(exception: Exception?) {
        dismissDialog()
        showToast(R.string.error_toast)
        exception?.let {
            printException(it)
        }
    }

    fun handleLoading(messageId: Int? = null) {
        messageId?.let {
            dialog.titleText = getString(it)
        }
        showDialog(KAlertDialog.PROGRESS_TYPE)
        dialog.show()
    }

    fun handleSuccess() {
        dismissDialog()
    }

    private fun printException(e: Exception) {
        e.printStackTrace()
    }

    private fun dismissDialog() {
        dialog.dismiss()
    }

    private fun showDialog(type: Int) {
        dialog.show()
    }
}
