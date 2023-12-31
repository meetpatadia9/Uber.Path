package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.ReasonAdapter
import com.ipsmeet.uberpath.databinding.ActivityReasonBinding
import com.ipsmeet.uberpath.dataclass.ReasonDataclass

class ReasonActivity : AppCompatActivity() {

    lateinit var binding: ActivityReasonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReasonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val reasons = arrayListOf<ReasonDataclass>()
        reasons.apply {
            add(ReasonDataclass(R.drawable.img_spend_or_save, "Spend or \nsave daily", false))
            add(ReasonDataclass(R.drawable.img_fast, "Fast my\ntransactions", false))
            add(ReasonDataclass(R.drawable.img_friend, "Payments\nto friends", false))
            add(ReasonDataclass(R.drawable.img_credit_card, "Online\npayments", false))
            add(ReasonDataclass(R.drawable.img_travel, "Spend while\ntravelling", false))
            add(ReasonDataclass(R.drawable.img_business_plan, "Your financial\nassets", false))
        }

        binding.recyclerViewReasons.apply {
            layoutManager = GridLayoutManager(this@ReasonActivity, 2, LinearLayoutManager.VERTICAL, false)
            adapter = ReasonAdapter(this@ReasonActivity, reasons)
        }

        binding.btnContinue.setOnClickListener {
            startActivity(
                Intent(this, SetPINActivity::class.java)
            )
        }
    }

}