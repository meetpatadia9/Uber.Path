package com.ipsmeet.uberpath.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityQrScanBinding

class QRScanActivity : AppCompatActivity() {

    lateinit var binding: ActivityQrScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.imgVYourQR.elevation = 15F
    }
}