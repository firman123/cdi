package com.app.cdipoc.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.app.cdipoc.R

class SuccessDialog(val context: Context) {
    private lateinit var alertDialog: AlertDialog

    fun showDialog(listener: DialogListener) {
        val view = LayoutInflater.from(context).inflate(R.layout.verify_success_dialog, null)
        val btnOk = view.findViewById(R.id.btn_ok) as Button

        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        alertDialog = builder.create()
        alertDialog.setView(view)
        alertDialog.show()

        btnOk.setOnClickListener {
            alertDialog.dismiss()
            listener.approve()
        }
    }

    public interface DialogListener {
        public fun approve()
    }
}