package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ipsmeet.uberpath.databinding.ActivityCardStyleBinding

class CardStyleActivity : AppCompatActivity() {

    lateinit var binding: ActivityCardStyleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardStyleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.imgVCardStyle1.setOnClickListener {
            startActivity(
                Intent(this, CardDetailsActivity::class.java)
                    .putExtra("cardStyle", "cardOne")
            )
        }

        binding.imgVCardStyle2.setOnClickListener {
            startActivity(
                Intent(this, CardDetailsActivity::class.java)
                    .putExtra("cardStyle", "cardTwo")
            )
        }

        binding.imgVCardStyle3.setOnClickListener {
            startActivity(
                Intent(this, CardDetailsActivity::class.java)
                    .putExtra("cardStyle", "cardThree")
            )
        }
    }

}