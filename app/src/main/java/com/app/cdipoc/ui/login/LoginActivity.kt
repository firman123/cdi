package com.app.cdipoc.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.cdipoc.databinding.ActivityLoginBinding
import com.app.cdipoc.ui.home.HomeActivity
import com.app.cdipoc.dialog.LoadingDialog
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.PrefManager
import com.app.cdipoc.ui.contact.ContactUsActivity
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityLoginBinding
    private val emails = arrayOf("gmail", "yahoo", "soho", "outlook", "rocketmail", "yandex", "ymail")

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

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email address"
            return
        }

        for (bannedEmail in emails) {
            if(email.contains(bannedEmail, false)) {
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
            .addOnSuccessListener { _ ->
                dialog.stopLoading()
                PrefManager.putString(this, Constant.PREFERENCE.USER_LOGIN, email)
                startActivity(Intent(this, HomeActivity::class.java))
                finish()

            }
            .addOnFailureListener { e ->
                dialog.stopLoading()
                Toast.makeText(this, "Error adding document", Toast.LENGTH_SHORT).show()
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