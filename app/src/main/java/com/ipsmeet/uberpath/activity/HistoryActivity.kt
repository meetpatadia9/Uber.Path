package com.ipsmeet.uberpath.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.HistoryAdapter
import com.ipsmeet.uberpath.databinding.ActivityHistoryBinding
import com.ipsmeet.uberpath.dataclass.HistoryDataClass
import com.ipsmeet.uberpath.dataclass.TransactionDataClass

class HistoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            statusBarColor = ContextCompat.getColor(this@HistoryActivity, R.color.green)
            WindowCompat.getInsetsController(window, decorView).isAppearanceLightStatusBars = false
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        val transactions = arrayListOf<HistoryDataClass>()

        val todayTransactions = arrayListOf<TransactionDataClass>()
        todayTransactions.apply {
            add(TransactionDataClass(R.drawable.img_basketball, "Sports Shop", "Payment", "$15.99", "December 28"))
            add(TransactionDataClass(R.drawable.img_money_receive, "From Test User9", "Received", "$61.18", "November 28"))
            add(TransactionDataClass(R.drawable.img_money_receive, "Bank of Baroda", "Deposit", "$2,045.00", "October 28"))
            add(TransactionDataClass(R.drawable.img_send, "To Test User1", "Sent", "$986.00", "September 28"))
        }

        val yesterdayTransactions = arrayListOf<TransactionDataClass>()
        yesterdayTransactions.apply {
            add(TransactionDataClass(R.drawable.img_line, "Line premium", "Payment", "$24.00", "December 28"))
            add(TransactionDataClass(R.drawable.img_bitcoin, "Bitcoin", "Investment", "$2,550.99", "November 28"))
            add(TransactionDataClass(R.drawable.img_paypal, "Paypal", "Freelance", "$2,328.00", "October 28"))
            add(TransactionDataClass(R.drawable.img_spotify, "Spotify premium", "Payment", "$24.00", "September 28"))
        }

        transactions.apply {
            add(HistoryDataClass("Today, Mar 20", todayTransactions))
            add(HistoryDataClass("Yesterday, Mar 19", yesterdayTransactions))
        }

        binding.recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity, LinearLayoutManager.VERTICAL, false)
            adapter = HistoryAdapter(this@HistoryActivity, transactions)
        }
    }

}