package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ipsmeet.uberpath.databinding.RecyclerSettingsWSwitchBinding
import com.ipsmeet.uberpath.dataclass.SettingSwitchDataClass

class SettingSwitchAdapter(val context: Context, val list: List<SettingSwitchDataClass>)
    : RecyclerView.Adapter<SettingSwitchAdapter.ListViewHolder>(){

    class ListViewHolder(val itemBinding: RecyclerSettingsWSwitchBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding = RecyclerSettingsWSwitchBinding.inflate(LayoutInflater.from(context), parent, false)
        return ListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemBinding.apply {
            with(list[position]) {
                txtSettingName.text = this.title
                txtSettingDescription.text = this.description
                switchOnOff.isChecked = this.switch
            }
        }
    }

}