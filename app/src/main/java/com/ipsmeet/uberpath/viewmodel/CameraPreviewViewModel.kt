package com.ipsmeet.uberpath.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.ipsmeet.uberpath.R
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

    private lateinit var cameraPreviewSurfaceTexture: SurfaceTexture


    fun checkCameraPermission(activity: Activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf("android.permission.CAMERA"),
                1)
            return
        }
    }

    fun openCamPreview(activity: Activity, context: Context, binding: ActivityCameraBinding, camPreview: LayoutCameraBinding) {
        activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.green)
        camPreview.camView.visibility = View.VISIBLE
        binding.layoutActivityCamera.visibility = View.GONE
        cameraPreviewLayout(activity, context, binding, camPreview)
    }

    fun closeCamPreview(activity: Activity, binding: ActivityCameraBinding) {
        activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.white)
        binding.camPreview.camView.visibility = View.GONE
        binding.layoutActivityCamera.visibility = View.VISIBLE
    }

    private fun cameraPreviewLayout(activity: Activity, context: Context, binding: ActivityCameraBinding, camView: LayoutCameraBinding) {
        //  BACK BUTTON
        camView.btnBack.setOnClickListener {
            closeCamPreview(activity, binding)
        }

        surfaceView = camView.cameraView
        val surfaceHolder = surfaceView.holder
        cameraView = CameraView(context)
        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                cameraPreviewSurfaceTexture = SurfaceTexture(0)
                previewCamera(context, activity)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { }

            override fun surfaceDestroyed(holder: SurfaceHolder) { }
        })

        //  CAPTURE BUTTON
        camView.btnCapture.setOnClickListener {
            context.startActivity(
                Intent(context, ResidentialProofActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
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
                            createCameraPreviewSession(activity)
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

    private fun createCameraPreviewSession(context: Activity) {
        try {
            val surface = surfaceView.holder.surface
            // Get the optimal preview size based on the SurfaceView dimensions
            val previewSize = chooseOptimalSize(context)

            cameraPreviewSurfaceTexture.setDefaultBufferSize(previewSize.width, previewSize.height)

            // Set the aspect ratio of the SurfaceView to match the preview size
            val layoutParams = surfaceView.layoutParams
            val previewAspectRatio = previewSize.width.toFloat() / previewSize.height.toFloat()
            val displayMetrics = context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels
            layoutParams.width = previewSize.width
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


    private fun getOptimalPreviewSize(context: Context): Size {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraIdList = cameraManager.cameraIdList
        val characteristics = cameraManager.getCameraCharacteristics(cameraIdList[0])
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        val supportedSizes = map?.getOutputSizes(SurfaceHolder::class.java) ?: emptyArray()

        val targetRatio = 16.0 / 9.0
        var optimalSize: Size? = null
        var minDiff = Double.MAX_VALUE

        for (size in supportedSizes) {
            val width = size.width
            val height = size.height
            val ratio = width.toDouble() / height.toDouble()

            if (Math.abs(ratio - targetRatio) < minDiff) {
                optimalSize = size
                minDiff = Math.abs(ratio - targetRatio)
            }
        }

        return optimalSize ?: supportedSizes[0]
    }

    private fun chooseOptimalSize(activity: Activity): Size {
        val cameraManager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0] // Choose the first available camera

        val characteristics = cameraManager.getCameraCharacteristics(cameraId)
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        val availableSizes = map?.getOutputSizes(SurfaceTexture::class.java)

        // Choose an appropriate size (you might need to implement your own logic here)
        // For example, choosing the largest available size:
        return availableSizes?.maxByOrNull { it.width * it.height } ?: Size(1920, 1080)
    }

}