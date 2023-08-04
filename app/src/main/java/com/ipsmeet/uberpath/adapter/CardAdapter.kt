package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.RecyclerCardsBinding
import com.ipsmeet.uberpath.dataclass.CardDataClass

class CardAdapter(val context: Context, private val cards: List<CardDataClass>)
    :RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(val itemBinding: RecyclerCardsBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemBinding = RecyclerCardsBinding.inflate(LayoutInflater.from(context), parent, false)
        return CardViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.apply {
            with (cards[position]) {
                Glide.with(context).load(this.cards).into(itemBinding.imgVCards)
            }
        }
    }
}