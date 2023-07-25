package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ipsmeet.uberpath.databinding.ActivityCreateNewPasswordBinding

class CreateNewPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNewPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreatePassword.setOnClickListener {
            startActivity(
                Intent(this, SignInActivity::class.java)
            )
            finish()
        }
    }
}