package com.app.cdipoc.ui.camera

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.cdipoc.R
import com.app.cdipoc.databinding.ActivityKtpCameraBinding
import com.app.cdipoc.dialog.LoadingDialog
import com.app.cdipoc.extension.ConverterImage
import com.app.cdipoc.extension.RotateImageHelper
import com.app.cdipoc.ui.result.ResultActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class KtpCameraActivity : AppCompatActivity() {
    private lateinit var photoFile: File
    private lateinit var binding: ActivityKtpCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var viewModel: CameraViewModel
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var camera: Camera
    private var enableTorch =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKtpCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initLoading()
        startCamera()
        initOutputDirectory()
        actionListener()
    }

    override fun onResume() {
        super.onResume()
        binding.ivCapture.visibility = View.GONE
        binding.viewFinder.visibility = View.VISIBLE
        binding.btnSend.visibility = View.GONE
        binding.btnCamera.visibility = View.VISIBLE
        binding.ivSplash.visibility = View.VISIBLE
        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun actionListener() {
        binding.ivBack.setOnClickListener { finish() }
        binding.btnCamera.setOnClickListener {
            takePhoto()
        }

        binding.ivSplash.setOnClickListener {
            enableTorch = when {
                !enableTorch -> {
                    camera.cameraControl.enableTorch(true)
                    true
                }
                else -> {
                    camera.cameraControl.enableTorch(false)
                    false
                }
            }
        }

        binding.btnSend.setOnClickListener {
            val imageString = ConverterImage().encodeImage(RotateImageHelper.rotateImage(photoFile.path))
            sendFoto(imageString)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
    }

    private fun initLoading () {
        loadingDialog = LoadingDialog(this)
    }


    private fun takePhoto() {
        // Get a stable reference of the
        // modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener,
        // which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    // set the saved uri to the image view
                    binding.ivCapture.visibility = View.VISIBLE
                    binding.ivCapture.setImageBitmap(RotateImageHelper.rotateImage(photoFile.path))
                    binding.viewFinder.visibility = View.GONE
                    cameraProvider.unbindAll()
                    
                    binding.btnCamera.visibility = View.GONE
                    binding.ivSplash.visibility = View.GONE
                    binding.btnSend.visibility = View.VISIBLE

                }
            })
    }


    private fun sendFoto(image: String) {
        loadingDialog.startLoading()


        val min = 100000000
        val max = 900000000
        val idTrans = Random().nextInt(max - min + 1) + min

        val body = HashMap<String, String>()
        body["transactionId"] = idTrans.toString()
        body["transactionSource"] = "MOBILE"
        body["customer_Id"] = "Test_customer"
        body["idCardImage"] = image

        viewModel.cdiOcr(this, body).observe(this) {
            loadingDialog.stopLoading()
            binding.ivBack.visibility = View.VISIBLE

            if (it.errorMessage.equals("SUCCESS", true)) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("data", it)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            cameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                display?.rotation?.let {
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
                       .also {
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
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e("KTPActivity", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

//    override fun onStart() {
//        super.onStart()
//        orientationEventListener.enable()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        orientationEventListener.disable()
//    }


    private val orientationEventListener by lazy {
        object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                Log.d("ROTATION_VALUE : ", orientation.toString())
//                if (orientation == UNKNOWN_ORIENTATION) {
//                    return
//                }
//
//                val rotation = when (orientation) {
//                    in 45 until 135 -> Surface.ROTATION_270
//                    in 135 until 225 -> Surface.ROTATION_180
//                    in 225 until 315 -> Surface.ROTATION_90
//                    else -> Surface.ROTATION_0
//                }
//
//                imageAnalysis.targetRotation = rotation
//                imageCapture.targetRotation = rotation
            }
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun initOutputDirectory() {
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }


    companion object {
        private const val TAG = "CameraXGFG"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}