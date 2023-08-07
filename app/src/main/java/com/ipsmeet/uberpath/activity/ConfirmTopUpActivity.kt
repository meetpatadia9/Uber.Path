package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityConfirmTopUpBinding
import com.ipsmeet.uberpath.databinding.LayoutBottomsheetTopUpSuccessBinding

class ConfirmTopUpActivity : AppCompatActivity() {

    lateinit var binding: ActivityConfirmTopUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmTopUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topUpAmt = intent.getStringExtra("topUpAmt")!!.toDouble()
        val fee = 3.0
        val total = topUpAmt + fee

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.txtTopUpAmt.text = getString(R.string.dollar_amt, topUpAmt.toString())
        binding.txtFeeAmt.text = getString(R.string.dollar_amt, fee.toString())
        binding.txtTotalAmt.text = getString(R.string.dollar_amt, total.toString())

        binding.btnConfirmTopUp.setOnClickListener {
            bottomSheet()
        }
    }

    private fun bottomSheet() {
        val bottomSheetBinding = LayoutBottomsheetTopUpSuccessBinding.inflate(LayoutInflater.from(this))
        val bottomSheet = BottomSheetDialog(this, R.style.CustomBottomSheet)
        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.show()
        bottomSheet.setCancelable(false)

        bottomSheetBinding.btnHome.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }
    }

}