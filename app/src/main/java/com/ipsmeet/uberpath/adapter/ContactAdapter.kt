package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.RecyclerContactsBinding
import com.ipsmeet.uberpath.dataclass.ContactDataClass

class ContactAdapter(val context: Context, private val contacts: List<ContactDataClass>, val listener: OnSelected)
    :RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){

    private var selected = -1

    class ContactViewHolder(val itemBinding: RecyclerContactsBinding)
        :RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemBinding = RecyclerContactsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContactViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.apply {
            with (contacts[position]) {
                Glide.with(context).load(this.profile).into(itemBinding.imgV)
                itemBinding.txtName.text = this.name

                itemView.setOnClickListener {
                    selected = position
                    notifyDataSetChanged()
                    listener.recipientSelection(this)
                }

                if (selected == position) {
                    itemView.setBackgroundResource(R.drawable.shape_green_button)
                } else {
                    itemView.setBackgroundResource(R.drawable.shape_grey_button)
                }
            }
        }
    }

    interface OnSelected {
        fun recipientSelection(contact: ContactDataClass)
    }

}