package com.ipsmeet.uberpath.dataclass

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.RecyclerTransactionDetailedBinding

class DetailedTransactionAdapter(val context: Context, private val transaction: List<TransactionDataClass>)
    : RecyclerView.Adapter<DetailedTransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(val itemBinding: RecyclerTransactionDetailedBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemBinding = RecyclerTransactionDetailedBinding.inflate(LayoutInflater.from(context), parent, false)
        return TransactionViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return transaction.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.apply {
            itemBinding.apply {
                with(transaction[position]) {
                    Glide.with(context).load(this.icon).into(imgV)
                    txtName.text = this.name
                    txtType.text = this.type
                    txtAmount.text = context.getString(R.string.minus, this.amount)
                    txtDate.text = this.date
                }
            }
        }
    }

}