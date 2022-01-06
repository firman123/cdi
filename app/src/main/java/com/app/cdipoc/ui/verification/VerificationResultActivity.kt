package com.app.cdipoc.ui.verification

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.cdipoc.databinding.ActivityVerificationResultBinding
import com.app.cdipoc.ui.home.HomeActivity

class VerificationResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundleData()
        actionListener()
    }

    private fun initBundleData() {
        val bundle = intent.extras
        if(bundle!=null) {
            val type = bundle.getString("type")
            val nik = bundle.getString("nik")
            val photo = bundle.getString("photo")
            val photoUri = Uri.parse(photo)

            binding.etNik.setText(nik)
            binding.profileImage.setImageURI(photoUri)
        }
    }

    private fun actionListener() {
        binding.btnResete.setOnClickListener {
            binding.etNik.setText("")
        }

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

}