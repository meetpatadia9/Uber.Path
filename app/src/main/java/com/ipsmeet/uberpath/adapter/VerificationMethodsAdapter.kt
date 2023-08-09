package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.activity.CreateCardActivity
import com.ipsmeet.uberpath.databinding.RecyclerVerificationMethodBinding
import com.ipsmeet.uberpath.dataclass.VerificationMethodDataClass

class VerificationMethodsAdapter(val context: Context, private val methods: List<VerificationMethodDataClass>)
    : RecyclerView.Adapter<VerificationMethodsAdapter.MethodsViewHolder>(){

    class MethodsViewHolder(val itemBinding: RecyclerVerificationMethodBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodsViewHolder {
        val itemBinding = RecyclerVerificationMethodBinding.inflate(LayoutInflater.from(context), parent, false)
        return MethodsViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return methods.size
    }

    override fun onBindViewHolder(holder: MethodsViewHolder, position: Int) {
        holder.itemBinding.apply {
            with(methods[position]) {
                Glide.with(context).load(this.icon).into(imgVMethod)
                txtMethod.text = this.name
            }

            root.setOnClickListener {
                context.startActivity(
                    Intent(context, CreateCardActivity::class.java)
                )
            }
        }
    }

}