package com.ipsmeet.uberpath.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.VerificationMethodsAdapter
import com.ipsmeet.uberpath.dataclass.VerificationMethodDataClass
import com.ipsmeet.uberpath.databinding.ActivityResidentialProofBinding
import com.ipsmeet.uberpath.viewmodel.SelectCountryViewModel

class ResidentialProofActivity : AppCompatActivity() {

    lateinit var binding: ActivityResidentialProofBinding

    private lateinit var selectCountry: SelectCountryViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResidentialProofBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE)
        selectCountry = ViewModelProvider(this)[SelectCountryViewModel::class.java]

        Glide.with(this)
            .load(sharedPreferences.getInt("countryFlag", R.drawable.flag_uk))
            .into(binding.imgVFlag)
        binding.txtCountryName.text = sharedPreferences.getString("userCountry", "United Kingdom")

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.txtChange.setOnClickListener {
            selectCountry.changeCountry(this, binding)
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