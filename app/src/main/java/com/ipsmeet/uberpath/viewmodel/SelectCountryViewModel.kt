package com.ipsmeet.uberpath.viewmodel

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.CountryAdapter
import com.ipsmeet.uberpath.databinding.ActivityCountryBinding
import com.ipsmeet.uberpath.databinding.LayoutBottomsheetCountryBinding
import com.ipsmeet.uberpath.dataclass.CountryDataclass

class SelectCountryViewModel: ViewModel() {

    fun showBottomSheet(context: Context, binding: ActivityCountryBinding) {
        val bottomSheetBinding = LayoutBottomsheetCountryBinding.inflate(LayoutInflater.from(context))
        val bottomSheet = BottomSheetDialog(context)
        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.show()

        //  CANCEL BUTTON
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
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CountryAdapter(context, countryList,
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