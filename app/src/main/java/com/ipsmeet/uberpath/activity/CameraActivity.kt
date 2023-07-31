package com.ipsmeet.uberpath.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.*
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ipsmeet.uberpath.databinding.ActivityCameraBinding
import com.otaliastudios.cameraview.CameraView
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class CameraActivity : AppCompatActivity() {

    lateinit var binding: ActivityCameraBinding

    private lateinit var surfaceView: SurfaceView
    private var cameraDevice: CameraDevice? = null
    private val cameraOpenCloseLock = Semaphore(1)
    private lateinit var cameraView: CameraView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnVerifyIdentity.setOnClickListener {
            openCamPreview()
        }
    }

    private fun openCamPreview() {
        binding.camPreview.camView.visibility = View.VISIBLE
        binding.layoutActivityCamera.visibility = View.GONE
        cameraPreviewLayout()
    }

    private fun closeCamPreview() {
        binding.camPreview.camView.visibility = View.GONE
        binding.layoutActivityCamera.visibility = View.VISIBLE
    }

    private fun cameraPreviewLayout() {
        val camView = binding.camPreview

        camView.btnBack.setOnClickListener {
            closeCamPreview()
        }

        surfaceView = camView.cameraView
        val surfaceHolder = surfaceView.holder
        cameraView = CameraView(this)
        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                previewCamera()
            }

            override fun surfaceChanged(holder: SurfaceHolder,  format: Int, width: Int, height: Int) { }

            override fun surfaceDestroyed(holder: SurfaceHolder) { }
        })

        camView.btnCapture.setOnClickListener {
            startActivity(
                Intent(this, ResidentialProofActivity::class.java)
            )
        }
    }

    private fun previewCamera() {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager

        try {
            val cameraIdList = cameraManager.cameraIdList
            for (cameraId in cameraIdList) {
                val characteristics = cameraManager.getCameraCharacteristics(cameraId)
                val cameraDirection = characteristics.get(CameraCharacteristics.LENS_FACING)

                if (cameraDirection == CameraCharacteristics.LENS_FACING_FRONT) {
                    if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                        throw RuntimeException("Time out waiting to lock camera opening.")
                    }

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return
                    }

                    cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
                        override fun onOpened(camera: CameraDevice) {
                            cameraOpenCloseLock.release()
                            cameraDevice = camera
                            createCameraPreviewSession()
                        }

                        override fun onDisconnected(camera: CameraDevice) {
                            cameraOpenCloseLock.release()
                            camera.close()
                            cameraDevice = null
                        }

                        override fun onError(camera: CameraDevice, error: Int) {
                            cameraOpenCloseLock.release()
                            camera.close()
                            cameraDevice = null
                            Log.e("CameraActivity", "CameraDevice.StateCallback.onError: $error")
                        }
                    }, null)
                    return
                }
            }
        } catch (e: CameraAccessException) {
            Log.e("CameraActivity", "Failed to open front camera: ${e.message}")
        }
    }

    private fun createCameraPreviewSession() {
        try {
            val surface = surfaceView.holder.surface
            // Get the optimal preview size based on the SurfaceView dimensions
            val previewSize = getOptimalPreviewSize()

            // Set the aspect ratio of the SurfaceView to match the preview size
            val layoutParams = surfaceView.layoutParams
            val previewAspectRatio = previewSize.width.toFloat() / previewSize.height.toFloat()
            val displayMetrics = resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels
            layoutParams.width = screenWidth
            layoutParams.height = screenWidth
            surfaceView.layoutParams = layoutParams

            cameraDevice?.createCaptureSession(
                listOf(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        try {
                            val captureRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                            captureRequestBuilder.addTarget(surface)

                            val captureRequest = captureRequestBuilder.build()
                            session.setRepeatingRequest(captureRequest, null, null)
                        }
                        catch (e: CameraAccessException) {
                            Log.e("CameraActivity", "Failed to set up camera capture request: ${e.message}")
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                        Log.e("CameraActivity", "CameraCaptureSession onConfigureFailed")
                    }
                }, null
            )
        }
        catch (e: CameraAccessException) {
            Log.e("CameraActivity", "Failed to create camera session: ${e.message}")
        }
    }

    private fun getOptimalPreviewSize(): Size {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        val cameraIdList = cameraManager.cameraIdList
        val characteristics = cameraManager.getCameraCharacteristics(cameraIdList[0])
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        val supportedSizes = map?.getOutputSizes(SurfaceHolder::class.java) ?: emptyArray()

        // You can modify the logic to select the desired preview size based on your requirements.
        // Here, we are just choosing the first supported size, but you may want to consider other factors.
        return supportedSizes[0]
    }

    override fun onResume() {
        super.onResume()
        closeCamPreview()
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.camPreview.camView.visibility == View.VISIBLE) {
            cameraDevice?.close()
            closeCamPreview()
        } else {
            super.onBackPressed()
        }
    }

}