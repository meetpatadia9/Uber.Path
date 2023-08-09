package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ipsmeet.uberpath.databinding.ActivityCreateCardBinding

class CreateCardActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetCard.setOnClickListener {
            startActivity(
                Intent(this, CardStyleActivity::class.java)
            )
        }
    }
}