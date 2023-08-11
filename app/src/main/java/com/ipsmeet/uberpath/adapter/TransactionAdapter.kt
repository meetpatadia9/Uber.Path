package com.ipsmeet.uberpath.adapter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.activity.DetailHistoryTransactionActivity
import com.ipsmeet.uberpath.databinding.RecyclerTransactionBinding
import com.ipsmeet.uberpath.dataclass.TransactionDataClass

class TransactionAdapter(val context: Context, val list: List<TransactionDataClass>)
    : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(val itemBinding: RecyclerTransactionBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemBinding = RecyclerTransactionBinding.inflate(LayoutInflater.from(context), parent, false)
        return TransactionViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.itemBinding.apply {
            with(list[position]) {
                Glide.with(context).load(this.icon).into(imgV)
                txtName.text = this.name
                txtType.text = this.type

                if (this.type == "Deposit" || this.type == "Received" || this.type == "Freelance") {
                    txtAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
                    txtAmount.text = context.getString(R.string.plus, this.amount)
                }
                else {
                    txtAmount.setTextColor(ContextCompat.getColor(context, R.color.blue))
                    txtAmount.text = context.getString(R.string.minus, this.amount)
                }

                root.setOnClickListener {
                    if (this.name == "Line premium") {
                        try {
                            context.startActivity(
                                Intent().setComponent(
                                    ComponentName(context, DetailHistoryTransactionActivity::class.java)
                                )
                            )
                        } catch (e: Exception) {
                            Log.e("Exception", e.message.toString())
                        }
                    }
                    else {
                        Toast.makeText(context, "Only Line premium available at this moment\uD83D\uDE42", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}