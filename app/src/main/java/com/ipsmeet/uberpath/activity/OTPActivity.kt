package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityOtpactivityBinding

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  BACK BUTTON
        binding.btnBack.setOnClickListener {
            finish()
        }

        //  Initialize shared-preference
        sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val email = intent.getStringExtra("email")

        //  String
        val spannableString = SpannableString(getString(R.string.txt_verification_instruction, email))

        //  Apply property to string
        spannableString.apply {
            setSpan(
                StyleSpan(android.graphics.Typeface.BOLD), 18, 18+email!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this@OTPActivity, R.color.blue)),
                18, 18+email.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.txtDescription.text = spannableString

        //  CONFIRM BUTTON
        binding.btnConfirm.setOnClickListener {
            updateUI()
        }
    }

    private fun updateUI() {
        editor.putBoolean("isLogged", true)
        editor.apply()

        startActivity(
            Intent(this, BiometricActivity::class.java)
        )
        finish()
    }
}