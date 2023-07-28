package com.ipsmeet.uberpath.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ipsmeet.uberpath.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}