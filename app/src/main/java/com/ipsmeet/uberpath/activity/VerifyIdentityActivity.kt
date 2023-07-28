package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityVerifyIdentityBinding

class VerifyIdentityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyIdentityBinding
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyIdentityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spannableString = SpannableString(getText(R.string.txt_verification_code_info))

        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this@VerifyIdentityActivity, R.color.green)),
            21, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.txtDescription.text = spannableString

        binding.userEmail.text = intent.getStringExtra("email")

        isSelected()

        binding.btnContinue.setOnClickListener {
            if (isChecked) {
                startActivity(
                    Intent(this, CreateNewPasswordActivity::class.java)
                )
                finish()
            } else {
                Toast.makeText(this, "Select", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isSelected() {
        binding.cardView.setOnClickListener {
            if (!isChecked) {
                Glide.with(this).load(R.drawable.img_checked).into(binding.imgVIsChecked)
                binding.cardView.setBackgroundResource(R.drawable.shape_edittext)
                isChecked = true
            }
            else {
                Glide.with(this).load(R.drawable.outline_circle_24).into(binding.imgVIsChecked)
                binding.cardView.setBackgroundResource(R.color.white)
                isChecked = false
            }
        }
    }
}