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
import com.ipsmeet.uberpath.viewmodel.SuperGlobal

class CountryAdapter(val context: Context, private val countryList: List<CountryDataclass>, private val listener: OnItemClick)
    : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){

    class CountryViewHolder(val itemBinding: RecyclerCountryBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemBinding = RecyclerCountryBinding.inflate(LayoutInflater.from(context), parent, false)
        return CountryViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.itemBinding.apply {
            with(countryList[position]) {
                Glide.with(context).load(this.flag).into(imgVFlag)
                txtCountryName.text = this.countryName

                root.setOnClickListener {
                    SuperGlobal.selectedCountry = position
                    this.isSelected = true
                    notifyDataSetChanged()
                    listener.onCountrySelect(this)
                }

                if (SuperGlobal.selectedCountry == position) {
                    imgVChecked.visibility = View.VISIBLE
                } else {
                    imgVChecked.visibility = View.GONE
                }
            }
        }
    }

    interface OnItemClick {
        fun onCountrySelect(country: CountryDataclass)
    }

}