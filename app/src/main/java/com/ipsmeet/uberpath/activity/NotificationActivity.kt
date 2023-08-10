package com.ipsmeet.uberpath.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.NotiAdapter
import com.ipsmeet.uberpath.databinding.ActivityNotificationBinding
import com.ipsmeet.uberpath.dataclass.HistoryDataClass
import com.ipsmeet.uberpath.dataclass.TransactionDataClass

class NotificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val todayNotification = arrayListOf<TransactionDataClass>()
        todayNotification.apply {
            add(
                TransactionDataClass(
                    R.drawable.img_reward,
                "Rewards",
                    "Loyal user rewards!\uD83D\uDE18",
                    "",
                    "5m ago"
                )
            )
            add(
                TransactionDataClass(
                    R.drawable.img_money_send,
                    "Money Transfer",
                    "You have successfully sent money to Maria of...",
                    "",
                    "25m ago"
                )
            )
        }

        val thisWeekNotification = arrayListOf<TransactionDataClass>()
        thisWeekNotification.apply {
            add(
                TransactionDataClass(
                    R.drawable.img_credit_card_orange,
                    "Payment Notification",
                    "Successfully paid!\uD83E\uDD11",
                    "",
                    "Mar 20"
                )
            )
            add(
                TransactionDataClass(
                    R.drawable.img_money_receive,
                    "Top Up",
                    "Your top up is successfully!",
                    "",
                    "Mar 13"
                )
            )
            add(
                TransactionDataClass(
                    R.drawable.img_money_send,
                    "Money Transfer",
                    "You have successfully sent money to Maria of...",
                    "",
                    "Mar 09"
                )
            )
            add(
                TransactionDataClass(
                    R.drawable.img_discount,
                    "Cashback 25%",
                    "You have received cashback!",
                    "",
                    "Mar 20"
                )
            )
            add(
                TransactionDataClass(
                    R.drawable.img_credit_card_orange,
                    "Payment Notification",
                    "Successfully paid!\uD83E\uDD11",
                    "",
                    "Mar 13"
                )
            )
        }

        val timePeriod = arrayListOf<HistoryDataClass>()
        timePeriod.apply {
            add(HistoryDataClass("Today", todayNotification))
            add(HistoryDataClass("This Week", thisWeekNotification))
        }

        binding.recyclerViewNotifications.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity, LinearLayoutManager.VERTICAL, false)
            adapter = NotiAdapter(this@NotificationActivity, timePeriod)
        }
    }

}