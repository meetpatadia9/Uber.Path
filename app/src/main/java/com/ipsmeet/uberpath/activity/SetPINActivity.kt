package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ipsmeet.uberpath.databinding.ActivitySetPinactivityBinding

class SetPINActivity : AppCompatActivity() {

    lateinit var binding: ActivitySetPinactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetPinactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  BACK BUTTON
        binding.btnBack.setOnClickListener {
            finish()
        }

        //  CREATE-PIN BUTTON
        binding.btnCreatePin.setOnClickListener {
            startActivity(
                Intent(this, CameraActivity::class.java)
            )
        }
    }
}