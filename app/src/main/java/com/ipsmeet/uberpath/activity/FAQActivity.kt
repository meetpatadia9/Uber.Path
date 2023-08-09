package com.ipsmeet.uberpath.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.adapter.FaqAdapter
import com.ipsmeet.uberpath.databinding.ActivityFaqBinding
import com.ipsmeet.uberpath.dataclass.FaqDataClass

class FAQActivity : AppCompatActivity() {

    lateinit var binding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val faqs = arrayListOf<FaqDataClass>()
        faqs.apply {
            add(
                FaqDataClass(
                "How do I create a Uber.Path account?",
                "You can create a Uber.Path account by: download and open the Uber.Path application first then select ..."
                )
            )
            add(
                FaqDataClass(
                "How to create a card for Uber.Path?",
                "You can select the create card menu then select  New Card select the continue button then you ..."
                )
            )
            add(
                FaqDataClass(
                "How to Top Up on Uber.Path?",
                "Click the Top Up menu then select the amount of money and the method then click the top up now button..."
                )
            )
        }

        binding.recyclerViewFaq.apply {
            layoutManager = LinearLayoutManager(this@FAQActivity, LinearLayoutManager.VERTICAL, false)
            adapter = FaqAdapter(this@FAQActivity, faqs)
        }
    }
}