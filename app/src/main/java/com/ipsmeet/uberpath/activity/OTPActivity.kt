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
import androidx.lifecycle.ViewModelProvider
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityOtpactivityBinding
import com.ipsmeet.uberpath.viewmodel.SpannableStringViewModel

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    lateinit var spannableString: SpannableStringViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spannableString = ViewModelProvider(this)[SpannableStringViewModel::class.java]

        //  BACK BUTTON
        binding.btnBack.setOnClickListener {
            finish()
        }

        //  Initialize shared-preference
        sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val email = intent.getStringExtra("email")

        binding.txtDescription.text = spannableString.verifyItsYou(email, this)

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