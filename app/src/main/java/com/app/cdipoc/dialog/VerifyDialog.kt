package com.app.cdipoc.dialog

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.app.cdipoc.R
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.PrefManager
import com.app.cdipoc.ui.camera.FaceCameraActivity

class VerifyDialog(val context: Context) {
    private lateinit var alertDialog: AlertDialog

    fun showDialog() {
        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.verify_dialog, null)
        builder.setView(view)
        builder.setCancelable(true)

        val editUrl = view.findViewById(R.id.et_url) as EditText
        val editThreshold = view.findViewById(R.id.et_threshold) as EditText
        val editUserId = view.findViewById(R.id.et_user_id) as EditText
        val editPassword = view.findViewById(R.id.et_password) as EditText
        val btnSave = view.findViewById(R.id.btn_save) as Button

        btnSave.setOnClickListener {
            if(editUrl.text.toString().isEmpty()) {
                editUrl.error = "Required"
                return@setOnClickListener
            }

            if(editThreshold.text.toString().isEmpty()) {
                editThreshold.error = "Required"
                return@setOnClickListener
            }

            if(editUserId.text.toString().isEmpty()) {
                editUserId.error = "Required"
                return@setOnClickListener
            }

            if(editPassword.text.toString().isEmpty()) {
                editPassword.error = "Required"
                return@setOnClickListener
            }

            PrefManager.putString(context, Constant.DUKCAPIL_VERIFY.URL, editUrl.text.toString())
            PrefManager.putString(context, Constant.DUKCAPIL_VERIFY.THRESHOLD, editThreshold.text.toString())
            PrefManager.putString(context, Constant.DUKCAPIL_VERIFY.USER_ID, editUserId.text.toString())
            PrefManager.putString(context, Constant.DUKCAPIL_VERIFY.PASSWORD, editPassword.text.toString())
            alertDialog.dismiss()

            context.startActivity(
                Intent(context, FaceCameraActivity::class.java)
                    .putExtra("type", "dukcapil")
            )
        }

        alertDialog = builder.create()
        alertDialog.show()

    }
}