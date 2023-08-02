package com.ipsmeet.uberpath.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.databinding.RecyclerCountryBinding
import com.ipsmeet.uberpath.dataclass.CountryDataclass

class CountryAdapter(val context: Context, private val countryList: List<CountryDataclass>, private val listener: OnItemClick)
    : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){

    private var checked = 0

    class CountryViewHolder(val itemBinding: RecyclerCountryBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemBinding = RecyclerCountryBinding.inflate(LayoutInflater.from(context), parent, false)
        return CountryViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.apply {
            with(countryList[position]) {
                itemBinding.imgVChecked.visibility = View.GONE

                Glide.with(context).load(this.flag).into(itemBinding.imgVFlag)
                itemBinding.txtCountryName.text = this.countryName

                itemView.setOnClickListener {
                    checked = position
                    notifyDataSetChanged()
                    listener.onCountrySelect(this)
                }

                if (checked == position) {
                    itemBinding.imgVChecked.visibility = View.VISIBLE
                } else {
                    itemBinding.imgVChecked.visibility = View.GONE
                }
            }
        }
    }

    interface OnItemClick {
        fun onCountrySelect(country: CountryDataclass)
    }
}