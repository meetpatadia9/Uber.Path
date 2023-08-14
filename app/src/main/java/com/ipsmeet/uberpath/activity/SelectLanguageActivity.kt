package com.ipsmeet.uberpath.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.LanguageAdapter
import com.ipsmeet.uberpath.databinding.ActivitySelectLanguageBinding
import com.ipsmeet.uberpath.dataclass.CountryDataclass

class SelectLanguageActivity : AppCompatActivity() {

    lateinit var binding: ActivitySelectLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val languageList = arrayListOf<CountryDataclass>()
        languageList.apply {
            add(CountryDataclass(R.drawable.flag_usa, "English (US)", false))
            add(CountryDataclass(R.drawable.flag_uk, "English (ENG)", false))
            add(CountryDataclass(R.drawable.flag_indonesia, "Indonesian", false))
            add(CountryDataclass(R.drawable.flag_russia, "Russia", false))
            add(CountryDataclass(R.drawable.flag_french, "French", false))
            add(CountryDataclass(R.drawable.flag_china, "Chinese", false))
            add(CountryDataclass(R.drawable.flag_japan, "Japanese", false))
            add(CountryDataclass(R.drawable.flag_germany, "Germany", false))
            add(CountryDataclass(R.drawable.flag_netherlands, "Netherlands", false))
        }

        binding.recyclerViewLanguage.apply {
            layoutManager = LinearLayoutManager(this@SelectLanguageActivity, LinearLayoutManager.VERTICAL, false)
            adapter = LanguageAdapter(this@SelectLanguageActivity, languageList,
                 object : LanguageAdapter.OnItemClick {
                     override fun onLanguageSelect(country: CountryDataclass) {

                     }
                 })
        }
    }

}