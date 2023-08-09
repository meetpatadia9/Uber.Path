package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.CardManageAdapter
import com.ipsmeet.uberpath.databinding.ActivityEditCardBinding
import com.ipsmeet.uberpath.dataclass.CardManageDataClass

class EditCardActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditCardBinding
    private var selected = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (intent.getStringExtra("cardStyle").toString()) {
            "cardOne" -> {
                Glide.with(this)
                    .load(R.drawable.img_card_style1)
                    .into(binding.imgVSelectedCardStyle)
            }
            "cardTwo" -> {
                Glide.with(this)
                    .load(R.drawable.img_card_style2)
                    .into(binding.imgVSelectedCardStyle)
            }
            "cardThree" -> {
                Glide.with(this)
                    .load(R.drawable.img_card_style3)
                    .into(binding.imgVSelectedCardStyle)
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        setSelectionOnTab()

        binding.txtPersonal.setOnClickListener {
            selected = 1
            setSelectionOnTab()
        }

        binding.txtManage.setOnClickListener {
            selected = 2
            setSelectionOnTab()
        }

        binding.txtDetails.setOnClickListener {
            selected = 3
            setSelectionOnTab()
        }

        val cardManageSettings = arrayListOf<CardManageDataClass>()
        cardManageSettings.apply {
            add(CardManageDataClass(R.drawable.img_credit_card_black, "Freeze physical card", true))
            add(CardManageDataClass(R.drawable.img_contactless, "Disable contactless", true))
            add(CardManageDataClass(R.drawable.img_lock, "Disable magstripe", false))
        }

        binding.recyclerViewManage.apply {
            layoutManager = LinearLayoutManager(this@EditCardActivity, LinearLayoutManager.VERTICAL, false)
            adapter = CardManageAdapter(this@EditCardActivity, cardManageSettings)
        }

        binding.btnSave.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    private fun setSelectionOnTab() {
        when (selected) {
            //  personal
            1 -> {
                binding.txtPersonal.apply {
                    setBackgroundResource(R.drawable.shape_card)
                    elevation = 5F
                }
                binding.txtManage.apply {
                    setBackgroundResource(0)
                    elevation = 0F
                }
                binding.txtDetails.apply {
                    setBackgroundResource(0)
                    elevation = 0F
                }
            }

            //  Manage
            2 -> {
                binding.txtPersonal.apply {
                    setBackgroundResource(0)
                    elevation = 0F
                }
                binding.txtManage.apply {
                    setBackgroundResource(R.drawable.shape_card)
                    elevation = 5F
                }
                binding.txtDetails.apply {
                    setBackgroundResource(0)
                    elevation = 0F
                }
            }

            //  detail
            3 -> {
                binding.txtPersonal.apply {
                    setBackgroundResource(0)
                    elevation = 0F
                }
                binding.txtManage.apply {
                    setBackgroundResource(0)
                    elevation = 0F
                }
                binding.txtDetails.apply {
                    setBackgroundResource(R.drawable.shape_card)
                    elevation = 5F
                }
            }
        }
    }

}