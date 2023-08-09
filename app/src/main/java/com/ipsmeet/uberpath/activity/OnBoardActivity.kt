package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.OnBoardAdapter
import com.ipsmeet.uberpath.databinding.ActivityOnBoardBinding
import java.util.*

class OnBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardBinding

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor
    private var isVisited = false

    private lateinit var timer: Timer
    private lateinit var onBoardAdapter: OnBoardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        onBoardAdapter = OnBoardAdapter(this)
        binding.viewpager.adapter = onBoardAdapter

        dotsIndicator()

        timer = Timer()
        timer.scheduleAtFixedRate(sliderTimer, 4000, 4000)

        binding.btnGetStarted.setOnClickListener {
            updateUI()
        }

        binding.txtSkip.setOnClickListener {
            updateUI()
        }
    }

    private fun dotsIndicator() {
        val dots = arrayOfNulls<ImageView>(onBoardAdapter.itemCount)

        //  Create a active dot drawable
        //  Highlight the first dot as active
        dots[0]?.setImageResource(R.drawable.active_view_pager)

        for (i in 0 until onBoardAdapter.itemCount) {
            dots[i] = ImageView(this)
            val height = 15
            val width = 55
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(width, height))
            dots[i]?.layoutParams = params
            //  Create an inactive dot drawable
            dots[i]?.setImageResource(R.drawable.inactive_view_pager)
            binding.layoutDots.addView(dots[i])
        }

        // Set up ViewPager's page-change-listener to update dot indicators
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                for (i in 0 until onBoardAdapter.itemCount) {
                    if (i == position) {
                        dots[i]?.setImageResource(R.drawable.active_view_pager)
                    } else {
                        dots[i]?.setImageResource(R.drawable.inactive_view_pager)
                    }
                }
            }
        })
    }

    private val sliderTimer = object : TimerTask() {
        override fun run() {
            runOnUiThread {
                if (binding.viewpager.currentItem < onBoardAdapter.itemCount - 1) {
                    binding.viewpager.currentItem = binding.viewpager.currentItem + 1
                } else {
                    binding.viewpager.currentItem = 0
                }
            }
        }
    }

    //  EXIT FROM ON-BOARD-ACTIVITY
    private fun updateUI() {
        editor.putBoolean("isVisited", true)
        editor.apply()

        startActivity(
            Intent(this, SignInActivity::class.java)
        )
        finish()
    }

    override fun onStart() {
        super.onStart()
        isVisited = sharedPreferences.getBoolean("isVisited", false)
        if (isVisited) {
            updateUI()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

}