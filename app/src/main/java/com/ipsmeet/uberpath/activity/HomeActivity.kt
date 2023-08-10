package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityHomeBinding
import com.ipsmeet.uberpath.fragments.ActivityFragment
import com.ipsmeet.uberpath.fragments.HomeFragment
import com.ipsmeet.uberpath.fragments.MyCardFragment
import com.ipsmeet.uberpath.fragments.ProfileFragment

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var bottomNavigationBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragments(HomeFragment(), "HOME")
        }

        bottomNavigationBar = binding.bottomNavigationBar

        bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> { replaceFragments(HomeFragment(), "HOME") }
                R.id.menu_myCard -> { replaceFragments(MyCardFragment(), "MY-CARD") }
                R.id.menu_activity -> { replaceFragments(ActivityFragment(), "ACTIVITY") }
                R.id.menu_profile -> { replaceFragments(ProfileFragment(), "PROFILE") }
                else -> { false }
            }
        }

        binding.fab.setOnClickListener {
            startActivity(
                Intent(this, QRScanActivity::class.java)
            )
        }
    }

    private fun replaceFragments(fragment: Fragment, tag: String): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_fragmentLoader, fragment, tag)
            .addToBackStack(null)
            .commit()
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

}