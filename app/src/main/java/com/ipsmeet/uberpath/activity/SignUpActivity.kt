package com.ipsmeet.uberpath.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ipsmeet.uberpath.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}