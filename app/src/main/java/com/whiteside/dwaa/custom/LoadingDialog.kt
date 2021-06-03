package com.whiteside.dwaa.custom

import android.app.AlertDialog
import android.content.Context
import android.widget.TextView
import com.whiteside.dwaa.R

class LoadingDialog(val context: Context) {
    var dialog: AlertDialog = AlertDialog.Builder(context)
        .setView(R.layout.loading_dialog)
        .create()

    fun show(messageId: Int) {
        dialog.show()
        dialog.findViewById<TextView>(R.id.message).setText(messageId)
    }

    fun dismiss() {
        dialog.cancel()
    }
}