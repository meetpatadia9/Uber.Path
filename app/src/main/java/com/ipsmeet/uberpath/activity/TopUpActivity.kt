package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityTopUpBinding

class TopUpActivity : AppCompatActivity() {

    lateinit var binding: ActivityTopUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.edtxtAmt.addTextChangedListener(textWatcher)

        binding.btnContinue.setOnClickListener {
            if (binding.edtxtAmt.text.toString().trim() != "") {
                startActivity(
                    Intent(this, ConfirmTopUpActivity::class.java)
                        .putExtra("topUpAmt", binding.edtxtAmt.text.toString().trim())
                )
            } else {
                Toast.makeText(this, "Enter Top Up amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        override fun afterTextChanged(s: Editable?) {
            if (binding.edtxtAmt.text.toString().trim() != "") {
                binding.edtxtAmt.setTypeface(null, Typeface.BOLD)
            } else {
                binding.edtxtAmt.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

}