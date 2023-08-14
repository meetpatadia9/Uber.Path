package com.ipsmeet.uberpath.activity

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        binding.edtxtFullName.apply {
            addTextChangedListener(textWatcher)
            setText(intent.getStringExtra("name"))
        }
        binding.txtOccupationSelect.text = intent.getStringExtra("occupation")
        binding.edtxtEmployerName.apply {
            addTextChangedListener(textWatcher)
            setText(intent.getStringExtra("employer"))
        }
        binding.edtxtPhoneNumber.apply {
            addTextChangedListener(textWatcher)
            setText(intent.getStringExtra("phoneNumber"))
        }
        binding.edtxtEmail.apply {
            addTextChangedListener(textWatcher)
            setText(intent.getStringExtra("email"))
        }

        binding.btnSave.setOnClickListener {
            finish()
        }
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        override fun afterTextChanged(s: Editable?) {
            when (s.hashCode()) {
                binding.edtxtFullName.text.hashCode() -> {
                    if (binding.edtxtFullName.text.toString().trim() != "") {
                        binding.edtxtFullName.setTypeface(null, Typeface.BOLD)
                    } else {
                        binding.edtxtFullName.setTypeface(null, Typeface.NORMAL)
                    }
                }

                binding.edtxtEmail.text.hashCode() -> {
                    if (binding.edtxtEmail.text.toString().trim() != "") {
                        binding.edtxtEmail.setTypeface(null, Typeface.BOLD)
                    } else {
                        binding.edtxtEmail.setTypeface(null, Typeface.NORMAL)
                    }
                }

                binding.edtxtEmployerName.text.hashCode() -> {
                    if (binding.edtxtEmployerName.text.toString().trim() != "") {
                        binding.edtxtEmployerName.setTypeface(null, Typeface.BOLD)
                    } else {
                        binding.edtxtEmployerName.setTypeface(null, Typeface.NORMAL)
                    }
                }

                binding.edtxtPhoneNumber.text.hashCode() -> {
                    if (binding.edtxtPhoneNumber.text.toString().trim() != "") {
                        binding.edtxtPhoneNumber.setTypeface(null, Typeface.BOLD)
                    } else {
                        binding.edtxtPhoneNumber.setTypeface(null, Typeface.NORMAL)
                    }
                }
            }
        }
    }

}