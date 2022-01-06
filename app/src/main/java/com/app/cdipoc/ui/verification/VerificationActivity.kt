package com.app.cdipoc.ui.verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.cdipoc.databinding.ActivityVerificationBinding
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.PrefManager
import com.app.cdipoc.model.ocr.Demographics
import com.app.cdipoc.ui.camera.FaceCameraActivity
import com.google.gson.Gson

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    private var type: String? = null
    private var nik = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundleData()
        getNik()
        actionListener()
    }

    private fun initBundleData() {
        val bundle = intent.extras
        bundle?.let {
            bundle.apply { ->
                type = getString("type")

                if(type.equals("biometric")) {
                    binding.tvHeader.text = "Biometric Verification"
                }
            }
        }
    }

    private fun getNik() {
        val dataKtp = PrefManager.getString(this, Constant.PREFERENCE.DATA_KTP, "")
        if(dataKtp?.isNotEmpty() == true) {
            val gson = Gson().fromJson(dataKtp, Demographics::class.java)
            binding.etNik.setText(gson.nik)
        }
     }

    private fun actionListener() {
        binding.btnRetake.setOnClickListener {
            binding.etNik.setText("")
        }

        binding.btnContinue.setOnClickListener {

            if(binding.etNik.text.toString().isEmpty()) {
                binding.etNik.setError("NIK is required")
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