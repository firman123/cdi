package com.app.cdipoc.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.app.cdipoc.R

class LoadingDialog(val context: Context) {
    private lateinit var alertDialog: AlertDialog

    fun startLoading() {
        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.loading_dialog)
        builder.setCancelable(false)

        alertDialog = builder.create()
        alertDialog.show()
    }

    fun stopLoading () {
        alertDialog.dismiss();
    }
}