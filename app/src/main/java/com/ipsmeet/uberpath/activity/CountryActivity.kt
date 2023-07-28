package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.CountryAdapter
import com.ipsmeet.uberpath.databinding.ActivityCountryBinding
import com.ipsmeet.uberpath.databinding.LayoutBottomsheetCountryBinding
import com.ipsmeet.uberpath.dataclass.CountryDataclass

class CountryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.selectCountry.setOnClickListener {
            showBottomSheet()
        }

        binding.btnContinue.setOnClickListener {
            startActivity(
                Intent(this, ReasonActivity::class.java)
            )
        }
    }

    private fun showBottomSheet() {
        val bottomSheetBinding = LayoutBottomsheetCountryBinding.inflate(LayoutInflater.from(this))
        val bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.show()

        bottomSheetBinding.btnCancel.setOnClickListener {
            bottomSheet.dismiss()
        }

        val countryList = arrayListOf<CountryDataclass>()
        countryList.apply {
            add(CountryDataclass(R.drawable.flag_uk, "United Kingdom"))
            add(CountryDataclass(R.drawable.flag_singapore, "Singapore"))
            add(CountryDataclass(R.drawable.flag_usa, "United States"))
            add(CountryDataclass(R.drawable.flag_china, "China"))
            add(CountryDataclass(R.drawable.flag_netherlands, "Netherlands"))
            add(CountryDataclass(R.drawable.flag_indonesia, "Indonesia"))
        }

        bottomSheetBinding.recyclerViewCountries.apply {
            layoutManager = LinearLayoutManager(this@CountryActivity, LinearLayoutManager.VERTICAL, false)
            adapter = CountryAdapter(this@CountryActivity, countryList,
            object : CountryAdapter.OnItemClick {
                override fun onCountrySelect(country: CountryDataclass) {
                    Glide.with(context).load(country.flag).into(binding.imgVFlag)
                    binding.txtCountryName.text = country.countryName
                    bottomSheet.dismiss()
                }
            })
        }
    }
}