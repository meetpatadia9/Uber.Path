package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.databinding.RecyclerProfileBinding
import com.ipsmeet.uberpath.dataclass.ProfileListDataClass

class SettingListAdapter(val context: Context, val list: List<ProfileListDataClass>, private val listener: OnClick)
    : RecyclerView.Adapter<SettingListAdapter.ListViewHolder>(){

    class ListViewHolder(val itemBinding: RecyclerProfileBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding = RecyclerProfileBinding.inflate(LayoutInflater.from(context), parent, false)
        return ListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemBinding.apply {
            with(list[position]) {
                Glide.with(context).load(this.icon).into(imgVIcon)
                txtName.text = this.text

                root.setOnClickListener {
                    listener.onClickListener(this)
                }
            }
        }
    }

    interface OnClick {
        fun onClickListener(profile: ProfileListDataClass)
    }

}