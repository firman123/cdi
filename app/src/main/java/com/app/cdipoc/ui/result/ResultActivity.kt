package com.app.cdipoc.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.cdipoc.databinding.ActivityResultBinding
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.PrefManager
import com.app.cdipoc.model.ocr.Demographics
import com.app.cdipoc.model.ocr.ResponseOcr
import com.app.cdipoc.ui.home.HomeActivity
import com.google.gson.Gson
import java.io.FileReader

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundleData()
        actionListener()

    }

    private fun initBundleData() {

        val bundle = intent.extras

        bundle?.let {
            bundle.apply {
                val ktp: Demographics? = getParcelable("data")
                if (ktp != null) {

                    binding.etNik.setText(ktp.nik)
                    binding.etNama.setText(ktp.nama)
                    binding.etTtl.setText(ktp.tempatLahir + " / " + ktp.tanggalLahir)
                    binding.etJk.setText(ktp.jenisKelamin)
                    binding.etAlamat.setText(ktp.alamat + ", " + ktp.kelurahanDesa + ", " + ktp.kecamatan + ", " + ktp.kotaKabupaten + ", " + ktp.provinsi)
                    binding.etAgama.setText(ktp.agama)
                    binding.etPekerjaan.setText(ktp.pekerjaan)

                    ktp.nik = binding.etNik.text.toString()
                    ktp.nama = binding.etNama.text.toString()
                    ktp.agama = binding.etAgama.text.toString()
                    ktp.pekerjaan = binding.etPekerjaan.text.toString()

                    val jsonKtp = Gson().toJson(ktp)
                    PrefManager.putString(
                        this@ResultActivity,
                        Constant.PREFERENCE.DATA_KTP,
                        jsonKtp
                    )
                }
            }
        }
    }

    private fun actionListener() {
        binding.btnContinue.setOnClickListener {
            gotoHome()
        }

        binding.btnRetake.setOnClickListener {
            finish()
        }

        binding.ivBack.setOnClickListener {
            gotoHome()
        }
    }

    private fun gotoHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}