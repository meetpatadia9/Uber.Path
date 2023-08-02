package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ipsmeet.uberpath.databinding.ActivityBiometricBinding

class BiometricActivity : AppCompatActivity() {

    lateinit var binding: ActivityBiometricBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  BACK BUTTON
        binding.btnBack.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java)
            )
        }

        //  ENABLE-BIOMETRIC BUTTON
        binding.btnEnableBiometric.setOnClickListener {
            Toast.makeText(this, "Your device does not support this. Buy new device", Toast.LENGTH_SHORT).show()
        }

        //  DO-IT-LATER BUTTON
        binding.txtWillDoLater.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java)
            )
        }
    }

}