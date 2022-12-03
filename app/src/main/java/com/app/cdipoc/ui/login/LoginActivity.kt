package com.app.cdipoc.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.cdipoc.databinding.ActivityLoginBinding
import com.app.cdipoc.dialog.LoadingDialog
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.PrefManager
import com.app.cdipoc.ui.contact.ContactUsActivity
import com.app.cdipoc.ui.home.HomeActivity
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityLoginBinding
    private val emails =
        arrayOf("gmail", "yahoo", "soho", "outlook", "rocketmail", "yandex", "ymail")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding.btnLogin.setOnClickListener {
            handleActionButton()
        }

        binding.tvContactUs.setOnClickListener {
            startActivity(Intent(this, ContactUsActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }
    }

    private fun handleActionButton() {
        val email = binding.etEmail.text.toString()
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email address"
            return
        }

        for (bannedEmail in emails) {
            if (email.contains(bannedEmail, false)) {
                binding.etEmail.error = "$bannedEmail not allowed, please enter corporate email!"
                return
            }
        }

        val dialog = LoadingDialog(this)
        dialog.startLoading()

        val user = hashMapOf(
            "email" to email
        )
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                PrefManager.putString(this, Constant.PREFERENCE.USER_LOGIN, email)
                readData(dialog)

            }
            .addOnFailureListener {
                dialog.stopLoading()
                Toast.makeText(this, "Error adding document", Toast.LENGTH_SHORT).show()
            }

    }

    private fun readData(dialog: LoadingDialog) {
        db.collection("endpoint_mobile")
            .get()
            .addOnSuccessListener {
                dialog.stopLoading()

                for (document in it) {
                    val title = document.data["title"].toString()
                    val url = document.data["url"].toString()

                    if (title.equals("enroll", true)) {
                        PrefManager.putString(this, Constant.BASE_URL_ENROLL, url)
                    } else if (title.equals("pasive_liveness", true)) {
                        PrefManager.putString(this, Constant.BASE_URL_PASSIVE, url)
                    } else if (title.equals("ocr", true)) {
                        PrefManager.putString(this, Constant.BASE_URL_OCR, url)
                    } else if (title.equals("biometric", true)) {
                        PrefManager.putString(this, Constant.BASE_URL_BIOMETRIC, url)
                    } else if (title.equals("dukcapil_verify", true)) {
                        PrefManager.putString(this, Constant.BASE_URL_DUKCAPIL, url)
                    } else if (title.equals("demography", true)) {
                        PrefManager.putString(this, Constant.BASE_URL_DEMOGRAPHY, url)
                    } else {
                        PrefManager.putString(this, Constant.BASE_URL_LOCAL_VERIFY, url)
                    }
                }


                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                dialog.stopLoading()
                Toast.makeText(this, "Failed downdload data", Toast.LENGTH_SHORT).show()
            }
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 20
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}