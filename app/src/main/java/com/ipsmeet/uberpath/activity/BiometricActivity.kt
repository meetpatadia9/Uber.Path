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

        binding.btnBack.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java)
            )
        }

        binding.btnEnableBiometric.setOnClickListener {
            Toast.makeText(this, "Your device does not support this. Buy new device", Toast.LENGTH_SHORT).show()
        }

        binding.txtWillDoLater.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java)
            )
        }
    }
}