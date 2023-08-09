package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ipsmeet.uberpath.R

class OnBoardAdapter(private val context: Context)
    : RecyclerView.Adapter<OnBoardAdapter.OnBoardingViewHolder>() {

    private val layouts = arrayOf(R.layout.on_board_one, R.layout.on_board_two)

    class OnBoardingViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val view = LayoutInflater.from(context).inflate(layouts[viewType], parent, false)
        return OnBoardingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return layouts.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) { }
}
