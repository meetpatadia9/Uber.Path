package com.ipsmeet.uberpath.activity

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.camera2.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.ipsmeet.uberpath.databinding.ActivityCameraBinding
import com.ipsmeet.uberpath.viewmodel.CameraPreviewViewModel

class CameraActivity : AppCompatActivity() {

    lateinit var binding: ActivityCameraBinding

    private var cameraDevice: CameraDevice? = null
    private lateinit var cameraPreview: CameraPreviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraPreview = ViewModelProvider(this)[CameraPreviewViewModel::class.java]

        binding.btnBack.setOnClickListener {
            finish()
        }

        //  VERIFY-IDENTITY [OPEN CAMERA] BUTTON
        binding.btnVerifyIdentity.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                cameraPreview.openCamPreview(this, applicationContext, binding, binding.camPreview)
            } else {
                cameraPreview.checkCameraPermission(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cameraPreview.closeCamPreview(this, binding)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.camPreview.camView.visibility == View.VISIBLE) {
            cameraDevice?.close()
            cameraPreview.closeCamPreview(this, binding)
        } else {
            super.onBackPressed()
        }
    }

}