package com.ipsmeet.uberpath.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.VerificationMethodsAdapter
import com.ipsmeet.uberpath.dataclass.VerificationMethodDataClass
import com.ipsmeet.uberpath.databinding.ActivityResidentialProofBinding

class ResidentialProofActivity : AppCompatActivity() {

    lateinit var binding: ActivityResidentialProofBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResidentialProofBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  BACK BUTTON
        binding.btnBack.setOnClickListener {
            finish()
        }

        val methods = arrayListOf<VerificationMethodDataClass>()
        methods.apply {
            add(VerificationMethodDataClass(R.drawable.img_passport, "Passport"))
            add(VerificationMethodDataClass(R.drawable.img_personal_id, "Identity card"))
            add(VerificationMethodDataClass(R.drawable.img_doc_text, "Digital document"))
        }

        binding.recyclerViewVerificationMethods.apply {
            layoutManager = LinearLayoutManager(this@ResidentialProofActivity, LinearLayoutManager.VERTICAL, false)
            adapter = VerificationMethodsAdapter(this@ResidentialProofActivity, methods)
        }
    }
}