package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.activity.DetailHistoryTransactionActivity
import com.ipsmeet.uberpath.databinding.RecyclerNotificationBinding
import com.ipsmeet.uberpath.databinding.RecyclerTransactionBinding
import com.ipsmeet.uberpath.dataclass.TransactionDataClass

class NotificationAdapter(val context: Context, val list: List<TransactionDataClass>)
    : RecyclerView.Adapter<NotificationAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(val itemBinding: RecyclerNotificationBinding)
        :RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemBinding = RecyclerNotificationBinding.inflate(LayoutInflater.from(context), parent, false)
        return TransactionViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.apply {
            with (list[position]) {
                Glide.with(context).load(this.icon).into(itemBinding.imgV)
                itemBinding.txtTitle.text = this.name
                itemBinding.txtDescription.text = this.type
                itemBinding.txtTime.text = this.date
            }
        }
    }

}