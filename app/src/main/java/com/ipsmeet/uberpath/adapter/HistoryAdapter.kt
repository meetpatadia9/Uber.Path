package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ipsmeet.uberpath.databinding.RecyclerHistoryBinding
import com.ipsmeet.uberpath.dataclass.HistoryDataClass

class HistoryAdapter(val context: Context, private val history: List<HistoryDataClass>)
    : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val itemBinding: RecyclerHistoryBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemBinding = RecyclerHistoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return HistoryViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return history.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemBinding.apply {
            with (history[position]) {
                txtTransactionDay.text = this.date

                recyclerViewTransaction.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = TransactionAdapter(context, this@with.transaction)
                }
            }
        }
    }

}