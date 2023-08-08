package com.ipsmeet.uberpath.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ipsmeet.uberpath.databinding.ActivityEditAccountBinding

class EditAccountActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.edtxtFullName.setText(intent.getStringExtra("name"))
        binding.txtOccupationSelect.text = intent.getStringExtra("occupation")
        binding.edtxtEmployerName.setText(intent.getStringExtra("employer"))
        binding.edtxtEmployerName.setText(intent.getStringExtra("phoneNumber"))
        binding.edtxtEmail.setText(intent.getStringExtra("email"))

        binding.btnSave.setOnClickListener {
            finish()
        }
    }

}