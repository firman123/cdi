package com.app.cdipoc.ui.camera

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.cdipoc.R
import com.app.cdipoc.databinding.ActivityFaceCameraBinding
import com.app.cdipoc.extension.ConverterImage
import com.app.cdipoc.dialog.LoadingDialog
import com.app.cdipoc.model.biometric.BiometricRequest
import com.app.cdipoc.ui.verification.VerificationResultActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.app.cdipoc.model.biometric.Biometric
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

import android.os.Build
import com.app.cdipoc.model.enrolldata.RequestEnroll
import com.app.cdipoc.extension.RotateImageHelper.rotateImage
import androidx.camera.core.ImageCapture





class FaceCameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaceCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var viewModel: CameraViewModel
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var savedUri: Uri
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var camera: Camera
    private lateinit var photoFile: File
    private var type: String? = null
    private var nik: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundleData()
        initViewModel()
        initLoading()
        startCamera()
        initOutputDirectory()
        actionListener()
    }

    private fun initOutputDirectory() {
        outputDirectory = getOutputDirectory()
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraProvider.unbindAll()
    }

    private fun initBundleData() {
        val bundle = intent.extras
        bundle?.let {
            bundle.apply {
                type = getString("type")
                nik = getString("nik")
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
    }

    private fun initLoading() {
        loadingDialog = LoadingDialog(this)
    }

    private fun actionListener() {
        binding.btnCamera.setOnClickListener {
            takePhoto()
        }

        binding.ivClose.setOnClickListener {
            binding.ivCapture.visibility = View.GONE
            binding.btnSend.visibility = View.GONE
            binding.ivClose.visibility = View.GONE
            binding.ivBack.visibility = View.GONE
            binding.viewFinder.visibility = View.VISIBLE
            binding.btnCamera.visibility = View.VISIBLE
            startCamera()

        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSend.setOnClickListener {

            when (type) {
                "passive" -> {
                    val imageString = ConverterImage().encodeImage(rotateImage(photoFile.path)).replace("\n", "")
                    sendPassiveLiveness(imageString)
                }
                "enroll_data" -> {
                    val imageString = ConverterImage().encodeImage(rotateImage(photoFile.path)).replace("\n", "")
                    sendEnrollData(nik, imageString, photoFile.path)
                }

                "biometric" -> {
                    val imageString = ConverterImage().encodeImage(rotateImage(photoFile.path)).replace("\n", "")
                    sendBiometric(nik, imageString, photoFile.path)
                }
            }
        }

    }


    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT,
                Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                    binding.ivCapture.visibility = View.VISIBLE
                    cameraProvider.unbindAll()

                    binding.viewFinder.visibility = View.GONE
                    binding.btnCamera.visibility = View.GONE
                    binding.ivClose.visibility = View.VISIBLE
                    binding.btnSend.visibility = View.VISIBLE
                    binding.ivCapture.setImageBitmap(rotateImage(photoFile.path))

                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            cameraProvider = cameraProviderFuture.get()

            val preview = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                display?.rotation?.let { it ->
                    Preview.Builder()
                        .setTargetRotation(it)
                        .build()
                        .also {
                            it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                        }
                }
            } else {
                windowManager.defaultDisplay.rotation.let {
                    Preview.Builder()
                        .setTargetRotation(it)
                        .build()
                        .also { it ->
                            it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                        }
                }
            }


            imageCapture = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                display?.rotation?.let {
                    ImageCapture.Builder()
                        .setTargetRotation(it)
                        .build()
                }
            } else {
                windowManager.defaultDisplay.rotation.let {
                    ImageCapture.Builder()
                        .setTargetRotation(it)
                        .build()
                }
            }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    @SuppressLint("SetTextI18n")
    private fun sendPassiveLiveness(image: String) {
        loadingDialog.startLoading()
        val body = HashMap<String, String>()
        body["image"] = image
        viewModel.liveness(this, body).observe(this, { it ->
            loadingDialog.stopLoading()
            if (it.status == 200) {
                binding.clResult.visibility = View.VISIBLE
                binding.btnSend.visibility = View.GONE
                binding.ivClose.visibility = View.GONE
                binding.ivBack.visibility = View.VISIBLE


                if(it.liveness?.status == false) {
                    binding.tvPercentage.setTextColor(getColor(R.color.red))
                    binding.ivCheck.setColorFilter(getColor(R.color.red))
                }

                binding.tvExplanation.text = "Result"
                (kotlin.math.round(it.liveness?.probability?.toDouble() ?: 0.0)
                    .toString() + "% " + it.liveness?.status).also {
                    binding.tvPercentage.text = it
                }
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendEnrollData(nik: String?, image: String, photoFile: String) {
        loadingDialog.startLoading()
        val body = RequestEnroll(nik, image)
        viewModel.enrollData(this, body).observe(this, { it ->
            loadingDialog.stopLoading()

            if (it.errorMessage.equals("SUCCESS", true)) {
                val intent = Intent(this, VerificationResultActivity::class.java)
                intent.putExtra("type", "enroll_data")
                intent.putExtra("nik", nik)
                intent.putExtra("photo", photoFile)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendBiometric(nik: String?, image: String, uri: String) {
        loadingDialog.startLoading()
        val biometricRequest = BiometricRequest()
        val biometric = Biometric()
        biometric.position = "F"
        biometric.template = null
        biometric.type = "Face"
        biometric.image = image
        val listBio = ArrayList<Biometric>()
        listBio.add(biometric)
        biometricRequest.biometrics = listBio

        val rnds = (1000..9999).random()
        biometricRequest.transactionId = "Digital$rnds"
        biometricRequest.component = "MOBILE"
        biometricRequest.customerId = "ekyc_customer_1"
        biometricRequest.digitalId = nik
        biometricRequest.requestType = "verify"
        biometricRequest.nik = nik
        biometricRequest.deviceId = "9885037442"
        biometricRequest.appVersion = 1.0
        biometricRequest.sdkVersion = 1.0
        biometricRequest.faceThreshold = 6.0
        biometricRequest.passiveLiveness = false
        biometricRequest.liveness = false
        biometricRequest.localVerification = true
        biometricRequest.isVerifyWithImage = false
        biometricRequest.verifyIdCardFaceImage = false

        viewModel.biometric(this, biometricRequest).observe(this, { it ->
            loadingDialog.stopLoading()

            if (it.errorMessage.equals("SUCCESS", true)) {
                if(it?.verificationScore?:0.0 < 0) {
                    Toast.makeText(this, "Not registered, please enroll before biometric verification", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this, VerificationResultActivity::class.java)
                    intent.putExtra("type", "biometric")
                    intent.putExtra("nik", nik)
                    intent.putExtra("photo", uri)
                    intent.putExtra("score", it.verificationScore)
                    intent.putExtra("match", it.verificationResult)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }


    companion object {
        private const val TAG = "FaceCamera"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}