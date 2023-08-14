package com.ipsmeet.uberpath.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.RecyclerLanguageBinding
import com.ipsmeet.uberpath.dataclass.CountryDataclass
import com.ipsmeet.uberpath.viewmodel.SuperGlobal

class LanguageAdapter(val context: Context, private val languageList: List<CountryDataclass>, private val listener: OnItemClick)
    : RecyclerView.Adapter<LanguageAdapter.CountryViewHolder>(){

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
        holder.itemBinding.apply {
            with(languageList[position]) {
                Glide.with(context).load(this.flag).into(imgVFlag)
                txtCountryName.text = this.countryName

                root.setOnClickListener {
                    SuperGlobal.selectedLanguage = position
                    notifyDataSetChanged()
                    listener.onLanguageSelect(this)
                }

                if (SuperGlobal.selectedLanguage == position) {
                    Glide.with(context)
                        .load(R.drawable.img_circle_checked)
                        .into(imgVChecked)
                } else {
                    Glide.with(context)
                        .load(R.drawable.img_circle_unchecked)
                        .into(imgVChecked)
                }
            }
        }
    }

    interface OnItemClick {
        fun onLanguageSelect(country: CountryDataclass)
    }
}