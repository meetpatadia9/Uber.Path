package com.ipsmeet.uberpath.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.SettingListAdapter
import com.ipsmeet.uberpath.adapter.SettingSwitchAdapter
import com.ipsmeet.uberpath.databinding.ActivityGeneralSettingBinding
import com.ipsmeet.uberpath.dataclass.ProfileListDataClass
import com.ipsmeet.uberpath.dataclass.SettingSwitchDataClass

class GeneralSettingActivity : AppCompatActivity() {

    lateinit var binding: ActivityGeneralSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val list1 = arrayListOf<ProfileListDataClass>()
        list1.apply {
            add(ProfileListDataClass(R.drawable.img_alert_blue, "Default Notification Actions"))
            add(ProfileListDataClass(R.drawable.img_setting, "Manage Notifications"))
        }
        binding.recyclerViewGeneralSetting.apply {
            layoutManager = LinearLayoutManager(this@GeneralSettingActivity, LinearLayoutManager.VERTICAL, false)
            adapter = SettingListAdapter(this@GeneralSettingActivity, list1,
                object : SettingListAdapter.OnClick {
                    override fun onClickListener(profile: ProfileListDataClass) { }
                })
        }

        val list2 = arrayListOf<SettingSwitchDataClass>()
        list2.apply {
            add(
                SettingSwitchDataClass(
                    "Default Notification Actions",
                    "You want to receive bill reminders before a bill is due.",
                    true
                )
            )
            add(
                SettingSwitchDataClass(
                    "Bills Calendar",
                    "You want to receive bill reminder emails before a bill...",
                    false
                )
            )
            add(
                SettingSwitchDataClass(
                    "Credit Score Calendar",
                    "You want to sync credit score reminders tou your device cale...",
                    true
                )
            )
        }
        binding.recyclerViewGeneralSettingSwitches.apply {
            layoutManager = LinearLayoutManager(this@GeneralSettingActivity, LinearLayoutManager.VERTICAL, false)
            adapter = SettingSwitchAdapter(this@GeneralSettingActivity, list2)
        }
    }
}