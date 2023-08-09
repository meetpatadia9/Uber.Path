package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ipsmeet.uberpath.databinding.RecyclerFaqBinding
import com.ipsmeet.uberpath.dataclass.FaqDataClass

class FaqAdapter(val context: Context, private val faqs: List<FaqDataClass>)
    : RecyclerView.Adapter<FaqAdapter.FaqViewHolder>(){

    class FaqViewHolder(val itemBinding: RecyclerFaqBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val itemBinding = RecyclerFaqBinding.inflate(LayoutInflater.from(context), parent, false)
        return  FaqViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return faqs.size
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        holder.apply {
            with(faqs[position]) {
                itemBinding.txtQue.text = this.que
                itemBinding.txtAns.text = this.ans
            }
        }
    }
}