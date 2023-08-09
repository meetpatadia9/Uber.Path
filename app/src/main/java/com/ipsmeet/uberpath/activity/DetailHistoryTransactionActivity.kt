package com.ipsmeet.uberpath.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityDetailHistoryTransactionBinding
import com.ipsmeet.uberpath.dataclass.DetailedTransactionAdapter
import com.ipsmeet.uberpath.dataclass.TransactionDataClass

class DetailHistoryTransactionActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailHistoryTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        binding.btnBack.apply {
            finish()
        }

        val lineTransaction = arrayListOf<TransactionDataClass>()
        lineTransaction.apply {
            add(TransactionDataClass(R.drawable.img_line, "Line premium", "Payment", "$24.00", "December 28"))
            add(TransactionDataClass(R.drawable.img_line, "Line premium", "Payment", "$24.00", "November 28"))
            add(TransactionDataClass(R.drawable.img_line, "Line premium", "Payment", "$24.00", "October 28"))
            add(TransactionDataClass(R.drawable.img_line, "Line premium", "Payment", "$24.00", "September 28"))
            add(TransactionDataClass(R.drawable.img_line, "Line premium", "Payment", "$24.00", "August 28"))
            add(TransactionDataClass(R.drawable.img_line, "Line premium", "Payment", "$24.00", "July 28"))
            add(TransactionDataClass(R.drawable.img_line, "Line premium", "Payment", "$24.00", "June 28"))
        }

        binding.recyclerViewTransaction.apply {
            layoutManager = LinearLayoutManager(this@DetailHistoryTransactionActivity, LinearLayoutManager.VERTICAL, false)
            adapter = DetailedTransactionAdapter(this@DetailHistoryTransactionActivity, lineTransaction)
        }
    }
}