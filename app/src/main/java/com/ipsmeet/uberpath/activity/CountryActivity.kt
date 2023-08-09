package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.CountryAdapter
import com.ipsmeet.uberpath.databinding.ActivityCountryBinding
import com.ipsmeet.uberpath.databinding.LayoutBottomsheetCountryBinding
import com.ipsmeet.uberpath.dataclass.CountryDataclass
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