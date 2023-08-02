package com.ipsmeet.uberpath.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.util.Log
import android.util.Size
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.ipsmeet.uberpath.activity.ResidentialProofActivity
import com.ipsmeet.uberpath.databinding.ActivityCameraBinding
import com.ipsmeet.uberpath.databinding.LayoutCameraBinding
import com.otaliastudios.cameraview.CameraView
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class CameraPreviewViewModel: ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private lateinit var surfaceView: SurfaceView
    private var cameraDevice: CameraDevice? = null
    private val cameraOpenCloseLock = Semaphore(1)
    @SuppressLint("StaticFieldLeak")
    private lateinit var cameraView: CameraView

    private fun checkCameraPermission(activity: Activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf("android.permission.CAMERA"),
                1)
            return
        }
    }

    fun openCamPreview(activity: Activity, context: Context, binding: ActivityCameraBinding, camPreview: LayoutCameraBinding) {
        camPreview.camView.visibility = View.VISIBLE
        binding.layoutActivityCamera.visibility = View.GONE
        cameraPreviewLayout(activity, context, binding, camPreview)
    }

    fun closeCamPreview(binding: ActivityCameraBinding) {
        binding.camPreview.camView.visibility = View.GONE
        binding.layoutActivityCamera.visibility = View.VISIBLE
    }

    private fun cameraPreviewLayout(activity: Activity, context: Context, binding: ActivityCameraBinding, camView: LayoutCameraBinding) {

        //  BACK BUTTON
        camView.btnBack.setOnClickListener {
            closeCamPreview(binding)
        }

        surfaceView = camView.cameraView
        val surfaceHolder = surfaceView.holder
        cameraView = CameraView(context)
        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                previewCamera(context, activity)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { }

            override fun surfaceDestroyed(holder: SurfaceHolder) { }
        })

        //  CAPTURE BUTTON
        camView.btnCapture.setOnClickListener {
            context.startActivity(
                Intent(context, ResidentialProofActivity::class.java)
            )
        }
    }

    private fun previewCamera(context: Context, activity: Activity) {
        val cameraManager = context.getSystemService(AppCompatActivity.CAMERA_SERVICE) as CameraManager

        try {
            val cameraIdList = cameraManager.cameraIdList
            for (cameraId in cameraIdList) {
                val characteristics = cameraManager.getCameraCharacteristics(cameraId)
                val cameraDirection = characteristics.get(CameraCharacteristics.LENS_FACING)

                if (cameraDirection == CameraCharacteristics.LENS_FACING_FRONT) {
                    if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                        throw RuntimeException("Time out waiting to lock camera opening.")
                    }

                    checkCameraPermission(activity)

                    cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
                        override fun onOpened(camera: CameraDevice) {
                            cameraOpenCloseLock.release()
                            cameraDevice = camera
                            createCameraPreviewSession(context)
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
        }
        catch (e: CameraAccessException) {
            Log.e("CameraActivity", "Failed to open front camera: ${e.message}")
        }
    }

    private fun createCameraPreviewSession(context: Context) {
        try {
            val surface = surfaceView.holder.surface
            // Get the optimal preview size based on the SurfaceView dimensions
            val previewSize = getOptimalPreviewSize(context)

            // Set the aspect ratio of the SurfaceView to match the preview size
            val layoutParams = surfaceView.layoutParams
            val previewAspectRatio = previewSize.width.toFloat() / previewSize.height.toFloat()
            val displayMetrics = context.resources.displayMetrics
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
                            val captureRequestBuilder = cameraDevice!!.createCaptureRequest(
                                CameraDevice.TEMPLATE_PREVIEW)
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

    private fun getOptimalPreviewSize(context: Context): Size {
        val cameraManager = context.getSystemService(AppCompatActivity.CAMERA_SERVICE) as CameraManager
        val cameraIdList = cameraManager.cameraIdList
        val characteristics = cameraManager.getCameraCharacteristics(cameraIdList[0])
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        val supportedSizes = map?.getOutputSizes(SurfaceHolder::class.java) ?: emptyArray()

        //  we are just choosing the first supported size
        return supportedSizes[0]
    }

}