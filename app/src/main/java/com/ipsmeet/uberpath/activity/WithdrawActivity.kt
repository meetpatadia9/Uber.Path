package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityWithdrawBinding
import com.ipsmeet.uberpath.databinding.LayoutBottomsheetWithdrawBinding

class WithdrawActivity : AppCompatActivity() {

    lateinit var binding: ActivityWithdrawBinding
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.edtxtAmt.addTextChangedListener(textWatcher)

        binding.btnWithdraw.setOnClickListener {
            bottomSheet()
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

    private fun bottomSheet() {
        val bottomSheetBinding = LayoutBottomsheetWithdrawBinding.inflate(LayoutInflater.from(this))
        val bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.show()

        bottomSheetBinding.layoutBank.setOnClickListener {
            if (isChecked) {
                Glide.with(this).load(R.drawable.img_checked).into(bottomSheetBinding.imgVIsChecked)
                isChecked = false
            } else {
                Glide.with(this).load(R.drawable.outline_circle_24).into(bottomSheetBinding.imgVIsChecked)
                isChecked = true
            }
        }

        bottomSheetBinding.btnConfirm.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }
    }

}