package com.app.cdipoc.ui.verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.cdipoc.databinding.ActivityVerificationBinding
import com.app.cdipoc.ui.camera.FaceCameraActivity

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    private var type: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundleData()
        actionListener()
    }

    private fun initBundleData() {
        val bundle = intent.extras
        bundle?.let {
            bundle.apply {
                type = getString("type")

                if(type.equals("biometric")) {
                    binding.tvHeader.text = "Biometric Verification"
                }
            }
        }
    }

    private fun actionListener() {
        binding.btnRetake.setOnClickListener {
            binding.etNik.setText("")
        }

        binding.btnContinue.setOnClickListener {

            if(binding.etNik.text.toString().isEmpty()) {
                binding.etNik.error = "NIK is required"
                return@setOnClickListener
            }

            if(binding.etNik.text.length != 16) {
                binding.etNik.error = "NIK must be 16 digit number"
                return@setOnClickListener
            }

            val intent = Intent(this, FaceCameraActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("nik", binding.etNik.text.toString())
            startActivity(intent)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}