package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.databinding.LayoutReasonBinding
import com.ipsmeet.uberpath.dataclass.ReasonDataclass

class ReasonAdapter(val context: Context, private val reasonList: List<ReasonDataclass>)
    : RecyclerView.Adapter<ReasonAdapter.ReasonViewHolder>() {

    class ReasonViewHolder(val itemBinding: LayoutReasonBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReasonViewHolder {
        val itemBinding = LayoutReasonBinding.inflate(LayoutInflater.from(context), parent, false)
        return ReasonViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return reasonList.size
    }

    override fun onBindViewHolder(holder: ReasonViewHolder, position: Int) {
        holder.itemBinding.apply {
            with(reasonList[position]) {
                Glide.with(context).load(this.icon).into(imgVReasonImg)
                txtReasonTxt.text = this.title
            }
        }
    }

}