package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.RecyclerTransactionBinding
import com.ipsmeet.uberpath.dataclass.TransactionDataClass

class TransactionAdapter(val context: Context, val list: List<TransactionDataClass>)
    : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(val itemBinding: RecyclerTransactionBinding)
        :RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemBinding = RecyclerTransactionBinding.inflate(LayoutInflater.from(context), parent, false)
        return TransactionViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.apply {
            with (list[position]) {
                Glide.with(context).load(this.icon).into(itemBinding.imgV)
                itemBinding.txtName.text = this.name
                itemBinding.txtType.text = this.type

                if (this.type == "Deposit" || this.type == "Received") {
                    itemBinding.txtAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
                    itemBinding.txtAmount.text = context.getString(R.string.plus, this.amount)
                }
                else {
                    itemBinding.txtAmount.setTextColor(ContextCompat.getColor(context, R.color.blue))
                    itemBinding.txtAmount.text = context.getString(R.string.minus, this.amount)
                }
            }
        }
    }

}