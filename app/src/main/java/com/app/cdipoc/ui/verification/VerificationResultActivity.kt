package com.app.cdipoc.ui.verification

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.app.cdipoc.R
import com.app.cdipoc.databinding.ActivityVerificationResultBinding
import com.app.cdipoc.extension.RotateImageHelper
import com.app.cdipoc.ui.home.HomeActivity
import java.text.DecimalFormat

class VerificationResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundleData()
        actionListener()
    }

    @SuppressLint("SetTextI18n")
    private fun initBundleData() {
        val bundle = intent.extras
        if(bundle!=null) {
            val type = bundle.getString("type")
            val nik = bundle.getString("nik")
            val photo = bundle.getString("photo")

            if(type.equals("biometric", false)) {
                binding.clValueEnroll.visibility = View.GONE
                val score = bundle.getDouble("score")
                val match = bundle.getBoolean("match")
                val df = DecimalFormat("#.######")
                val format = df.format(score)
                if(match) {
                    binding.tvBiometricSuccess.text = format.toString()
                    binding.tvMatch.text = "Match"
                } else {
                    binding.tvBiometricSuccess.setTextColor(getColor(R.color.red))
                    binding.ivCheckBiometric?.visibility = View.GONE
                    binding.tvMatch.setTextColor(getColor(R.color.red))
                    binding.tvBiometricSuccess.text = format.toString()
                    binding.tvMatch.text = "Unmatch"

                }
            } else {
                binding.clValueBiometric.visibility = View.GONE
            }

            val bitmap = RotateImageHelper.rotateImage(photo)
            binding.etNik.setText(nik)
            binding.profileImage.setImageBitmap(bitmap)
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