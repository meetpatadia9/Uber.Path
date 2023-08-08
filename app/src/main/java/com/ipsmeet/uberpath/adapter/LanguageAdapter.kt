package com.ipsmeet.uberpath.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.RecyclerLanguageBinding
import com.ipsmeet.uberpath.dataclass.CountryDataclass

class LanguageAdapter(val context: Context, private val languageList: List<CountryDataclass>, private val listener: OnItemClick)
    : RecyclerView.Adapter<LanguageAdapter.CountryViewHolder>(){

    private var checked = 0

    class CountryViewHolder(val itemBinding: RecyclerLanguageBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemBinding = RecyclerLanguageBinding.inflate(LayoutInflater.from(context), parent, false)
        return CountryViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.apply {
            with(languageList[position]) {
                Glide.with(context).load(this.flag).into(itemBinding.imgVFlag)
                itemBinding.txtCountryName.text = this.countryName

                itemView.setOnClickListener {
                    checked = position
                    notifyDataSetChanged()
                    listener.onCountrySelect(this)
                }

                if (checked == position) {
                    Glide.with(context).load(R.drawable.img_circle_checked).into(itemBinding.imgVChecked)
                } else {
                    Glide.with(context).load(R.drawable.img_circle_unchecked).into(itemBinding.imgVChecked)
                }
            }
        }
    }

    interface OnItemClick {
        fun onCountrySelect(country: CountryDataclass)
    }
}