package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.OnBoardAdapter
import com.ipsmeet.uberpath.databinding.ActivityOnBoardBinding
import java.util.*

class OnBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardBinding

    private lateinit var timer: Timer
    private lateinit var onBoardAdapter: OnBoardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        onBoardAdapter = OnBoardAdapter(this)

        binding.viewpager.adapter = onBoardAdapter

        dotsIndicator()

        timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(), 4000, 4000)

        binding.btnGetStarted.setOnClickListener {
            updateUI()
        }

        binding.txtSkip.setOnClickListener {
            updateUI()
        }
    }

    private fun dotsIndicator() {
        val dots = arrayOfNulls<ImageView>(onBoardAdapter.itemCount)

        // Highlight the first dot as active
        dots[0]?.setImageResource(R.drawable.active_view_pager) // Create a active dot drawable

        for (i in 0 until onBoardAdapter.itemCount) {
            dots[i] = ImageView(this)
            val height = 15
            val width = 55
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(width, height))
            dots[i]?.layoutParams = params
            dots[i]?.setImageResource(R.drawable.inactive_view_pager) // Create an inactive dot drawable
            binding.layoutDots.addView(dots[i])
        }

        // Set up ViewPager page change listener to update dot indicators
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

    fun updateUI() {
        startActivity(
            Intent(this, SignInActivity::class.java)
        )
        finish()
    }

    inner class SliderTimer : TimerTask() {
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

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}