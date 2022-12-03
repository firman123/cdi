package com.app.cdipoc.ui.dukcapil

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.app.cdipoc.databinding.ActivityDukcapilBinding
import com.app.cdipoc.dialog.LoadingDialog
import com.app.cdipoc.dialog.SuccessDialog
import com.app.cdipoc.extension.RotateImageHelper
import com.app.cdipoc.model.localverify.Biometric
import com.app.cdipoc.model.localverify.LocalVerifyRequest
import com.app.cdipoc.ui.camera.CameraViewModel
import com.app.cdipoc.ui.camera.FaceCameraActivity
import java.util.*
import kotlin.collections.ArrayList

private const val LOCAL_VERIFY = "local_verify"

class DukcapilActivity : AppCompatActivity() {
    private var type: String? = null
    private lateinit var binding: ActivityDukcapilBinding
    private lateinit var imageString: String
    private lateinit var viewModel: CameraViewModel
    private lateinit var loadingDialog: LoadingDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityDukcapilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoading()
        initBundleData()
        initViewModel()
        actionListener()
    }

    private fun initLoading() {
        loadingDialog = LoadingDialog(this)
    }

    private fun initBundleData () {
        val bundle = intent.extras
        bundle?.let { bundle.apply {
            type = getString("type")
            if(type.equals(LOCAL_VERIFY)) {
                binding.tvHeader.text = "Local Verify"
            }
        } }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
    }

    private fun actionListener() {
        binding.btnCapture.setOnClickListener {
            resultLauncer.launch( Intent(this, FaceCameraActivity::class.java)
                .putExtra("type", type))
        }

        binding.btnResete.setOnClickListener {
            binding.etNik.setText("")
        }

        binding.btnProcess.setOnClickListener {
            if(imageString.isEmpty()) {
                Toast.makeText(this, "Image can't empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.etNik.text.toString().isEmpty()) {
                binding.etNik.error = "NIK can't empty!"
                return@setOnClickListener
            }

            if(type.equals(LOCAL_VERIFY)) {
                sendLocalVerify()
            }
        }
    }

    private fun sendLocalVerify() {
        loadingDialog.startLoading()
        val biometric = Biometric(imageString, "F", null, "Face")
        val listBiometric = ArrayList<Biometric>()
        listBiometric.add(biometric)


        val min = 100000000
        val max = 900000000
        val idTrans = Random().nextInt(max - min + 1) + min

        val data = LocalVerifyRequest("1.0", listBiometric,
            "MOBILE","ekyc_customer_1",
            "9885037442", binding.etNik.text.toString(),"6",
            false, false, true,
            binding.etNik.text.toString(), "false", "verify", "1.0", idTrans.toString(), false
        )
        viewModel.local(this, data).observe(this) { it ->
            loadingDialog.stopLoading()
            if (it.errorMessage.equals("Sukses")) {
                val dialog = SuccessDialog(this)
                dialog.showDialog(object: SuccessDialog.DialogListener{
                    override fun approve() {
                        finish();
                    }

                })
            } else {
                Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private var resultLauncer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            val data: Intent? = it.data
            imageString = data?.getStringExtra("image_string") ?: ""
            val imagePath = data?.getStringExtra("image_path") ?: ""
            setImage(imagePath)
        }
    }


    private fun setImage(imagePath: String) {
        binding.ivDukcapil.setImageBitmap(RotateImageHelper.rotateImage(imagePath))
    }
}