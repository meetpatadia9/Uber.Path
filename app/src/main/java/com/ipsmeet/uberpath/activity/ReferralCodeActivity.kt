package com.ipsmeet.uberpath.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ipsmeet.uberpath.databinding.ActivityReferralCodeBinding
import com.ipsmeet.uberpath.viewmodel.SpannableStringViewModel

class ReferralCodeActivity : AppCompatActivity() {

    lateinit var binding: ActivityReferralCodeBinding
    private lateinit var spannableString: SpannableStringViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReferralCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spannableString = ViewModelProvider(this)[SpannableStringViewModel::class.java]

        binding.txt1.text = spannableString.free20dollars(this)

        binding.imgVCopy.setOnClickListener {
            val clipBoardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Referral Code", binding.txtCode.text.toString())
            clipBoardManager.setPrimaryClip(clipData)
        }

        binding.txt3.text = spannableString.free3dollars(this)
    }

}