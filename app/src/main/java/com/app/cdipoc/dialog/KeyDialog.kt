package com.app.cdipoc.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.app.cdipoc.R
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.PrefManager


class KeyDialog(val context: Context) {
    private lateinit var alertDialog: AlertDialog

    fun showDialog() {
        val builder = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context)
            .inflate(R.layout.key_dialog, null)
        builder.setView(view)
        builder.setCancelable(true)

        val editAPPId = view.findViewById(R.id.et_app_id) as EditText
        val editApiKey = view.findViewById(R.id.et_api_key) as EditText
        val btnSave = view.findViewById(R.id.btn_save) as Button

        btnSave.setOnClickListener {
            if(editAPPId.text.toString().isEmpty()) {
                editAPPId.error  = "Required"
                return@setOnClickListener
            }

            if(editApiKey.text.toString().isEmpty()) {
                editApiKey.error = "Required"
                return@setOnClickListener
            }

            PrefManager.putString(context, Constant.HEADER.APP_ID, editAPPId.text.toString())
            PrefManager.putString(context, Constant.HEADER.API_KEY, editApiKey.text.toString())
            alertDialog.dismiss()
        }

        alertDialog = builder.create()
        alertDialog.show()
    }
}