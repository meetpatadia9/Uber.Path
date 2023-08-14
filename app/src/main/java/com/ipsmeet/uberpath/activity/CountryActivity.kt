package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ipsmeet.uberpath.databinding.ActivityCountryBinding
import com.ipsmeet.uberpath.viewmodel.SelectCountryViewModel

class CountryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCountryBinding
    private lateinit var selectCountry: SelectCountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectCountry = ViewModelProvider(this)[SelectCountryViewModel::class.java]

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.selectCountry.setOnClickListener {
            selectCountry.showBottomSheet(this, binding)
        }

        binding.btnContinue.setOnClickListener {
            startActivity(
                Intent(this, ReasonActivity::class.java)
            )
        }
    }

}