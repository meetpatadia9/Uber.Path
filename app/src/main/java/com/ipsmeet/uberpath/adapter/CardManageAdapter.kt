package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.databinding.RecyclerCardManageBinding
import com.ipsmeet.uberpath.dataclass.CardManageDataClass

class CardManageAdapter(val context: Context, private val manageSettings: List<CardManageDataClass>)
    : RecyclerView.Adapter<CardManageAdapter.SettingViewHolder>(){

    class SettingViewHolder(val itemBinding: RecyclerCardManageBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val itemBinding = RecyclerCardManageBinding.inflate(LayoutInflater.from(context), parent, false)
        return SettingViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return manageSettings.size
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.apply {
            with (manageSettings[position]) {
                Glide.with(context).load(this.icon).into(itemBinding.imgVIcon)
                itemBinding.txtName.text = this.name
                itemBinding.switchOnOff.isChecked = this.isChecked
            }
        }
    }
}