package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityVerifyIdentityBinding
import com.ipsmeet.uberpath.viewmodel.SpannableStringViewModel

class VerifyIdentityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyIdentityBinding
    private var isChecked = false

    private lateinit var spannableString: SpannableStringViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyIdentityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spannableString = ViewModelProvider(this)[SpannableStringViewModel::class.java]

        binding.txtDescription.text = spannableString.sendSecurityCode(this)
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