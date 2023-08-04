package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityHomeBinding
import com.ipsmeet.uberpath.fragments.ActivityFragment
import com.ipsmeet.uberpath.fragments.HomeFragment
import com.ipsmeet.uberpath.fragments.MyCardFragment
import com.ipsmeet.uberpath.fragments.ProfileFragment

class HomeActivity : AppCompatActivity() {

    lateinit var  binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.layout_fragmentLoader, HomeFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.bottomNavigationBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_fragmentLoader, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                R.id.menu_myCard -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_fragmentLoader, MyCardFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                R.id.menu_activity -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_fragmentLoader, ActivityFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                R.id.menu_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_fragmentLoader, ProfileFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                else -> { false }
            }
        }

        binding.fab.setOnClickListener {
            startActivity(
                Intent(this, QRScanActivity::class.java)
            )
        }
    }

}