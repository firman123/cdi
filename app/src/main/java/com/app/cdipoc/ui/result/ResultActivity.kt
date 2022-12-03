package com.app.cdipoc.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.cdipoc.databinding.ActivityResultBinding
import com.app.cdipoc.extension.Constant
import com.app.cdipoc.extension.PrefManager
import com.app.cdipoc.model.demography.DemographyRequest
import com.app.cdipoc.model.ocr.Demographics
import com.app.cdipoc.model.ocr.ResponseOcr
import com.app.cdipoc.ui.camera.CameraViewModel
import com.app.cdipoc.ui.home.HomeActivity
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: CameraViewModel
    private var processDemography = false
    private var ocrResult: ResponseOcr? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initBundleData()
        actionListener()

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
    }

    private fun initBundleData() {
        val bundle = intent.extras
        bundle?.let {
            bundle.apply {
                ocrResult = getParcelable("data")
                val ktp = ocrResult?.demographics
                if (ktp != null) {

                    binding.etNik.setText(ktp.nik)
                    binding.etNama.setText(ktp.nama)
                    binding.etTtl.setText(ktp.tempatLahir + " / " + ktp.tanggalLahir)
                    binding.etJk.setText(ktp.jenisKelamin)
                    binding.etAlamat.setText(ktp.alamat)
                    binding.etRtrw.setText(ktp.rtRw)
                    binding.etDesa.setText(ktp.kelurahanDesa)
                    binding.etKecamatan.setText(ktp.kecamatan)
                    binding.etKabupaten.setText(ktp.kotaKabupaten)
                    binding.etProvinsi.setText(ktp.provinsi)
                    binding.etAgama.setText(ktp.agama)
                    binding.etStatusPerkawinan.setText(ktp.statusPerkawinan)
                    binding.etPekerjaan.setText(ktp.pekerjaan)
                    binding.etKewarganegaraan.setText(ktp.kewarganegaraan)
                    binding.etBerlakuHingga.setText(ktp.berlakuHingga)

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

                    processDemography = true
                    binding.btnContinue.text = "Process Demography"
                }
            }
        }
    }

    private fun actionListener() {
        binding.btnContinue.setOnClickListener {
            if(processDemography) {
                val demographics = ocrResult?.demographics
//                viewModel.demography(this, DemographyRequest(demographics?.alamat, "", "", demographics?.jenisKelamin, demographics?.nama, demographics?.nik, ) )
            } else {
                gotoHome()
            }
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