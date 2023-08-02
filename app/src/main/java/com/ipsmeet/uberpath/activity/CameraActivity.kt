package com.ipsmeet.uberpath.activity

import android.hardware.camera2.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

        //  BACK BUTTON
        binding.btnBack.setOnClickListener {
            finish()
        }

        //  VERIFY-IDENTITY [OPEN CAMERA] BUTTON
        binding.btnVerifyIdentity.setOnClickListener {
            cameraPreview.openCamPreview(this, applicationContext, binding, binding.camPreview)
        }
    }

    override fun onResume() {
        super.onResume()
        cameraPreview.closeCamPreview(binding)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.camPreview.camView.visibility == View.VISIBLE) {
            cameraDevice?.close()
            cameraPreview.closeCamPreview(binding)
        } else {
            super.onBackPressed()
        }
    }

}