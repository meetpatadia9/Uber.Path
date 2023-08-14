package com.ipsmeet.uberpath.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ReasonViewHolder, position: Int) {
        holder.itemBinding.apply {
            with(reasonList[position]) {
                Glide.with(context).load(this.icon).into(imgVReasonImg)
                txtReasonTxt.text = this.title

                root.setOnClickListener {
                    if (!this.isSelected) {
                        this.isSelected = true
                        notifyDataSetChanged()
                    } else {
                        this.isSelected = false
                        notifyDataSetChanged()
                    }
                }

                if (this.isSelected) {
                    root.setBackgroundResource(R.drawable.shape_btn_green_filled)
                    txtReasonTxt.setTextColor(ContextCompat.getColor(context, R.color.white))
                    imgVReasonImg.setColorFilter(ContextCompat.getColor(context, R.color.white))
                } else {
                    root.setBackgroundResource(R.drawable.shape_grey_button)
                    txtReasonTxt.setTextColor(ContextCompat.getColor(context, R.color.blue))
                    imgVReasonImg.setColorFilter(ContextCompat.getColor(context, R.color.black))
                }
            }
        }
    }

}