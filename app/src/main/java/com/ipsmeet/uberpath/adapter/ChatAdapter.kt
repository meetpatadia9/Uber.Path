package com.ipsmeet.uberpath.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ipsmeet.uberpath.databinding.RecyclerChatReceiverBinding
import com.ipsmeet.uberpath.databinding.RecyclerChatSenderBinding
import com.ipsmeet.uberpath.dataclass.ChatDataClass

class ChatAdapter(val context: Context, private val messages: List<ChatDataClass>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemSend = 1
    private val itemReceive = 2

    class SenderViewHolder(val itemBinding: RecyclerChatSenderBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    class ReceiverViewHolder(val itemBinding: RecyclerChatReceiverBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == itemSend) {
            val itemBinding = RecyclerChatSenderBinding.inflate(LayoutInflater.from(context), parent, false)
            SenderViewHolder(itemBinding)
        } else {
            val itemBinding = RecyclerChatReceiverBinding.inflate(LayoutInflater.from(context), parent, false)
            ReceiverViewHolder(itemBinding)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].sender == "Bot") {
            itemReceive
        } else {
            itemSend
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.javaClass == SenderViewHolder::class.java) {
            //  SENDER
            val viewHolder: SenderViewHolder = holder as SenderViewHolder
            viewHolder.itemBinding.apply {
                with(messages[position]) {
                    txtMsg.text = this.message
                }
            }
        } else {
            //  RECEIVER
            val viewHolder: ReceiverViewHolder = holder as ReceiverViewHolder
            viewHolder.itemBinding.apply {
                with(messages[position]) {
                    txtMsg.text = this.message
                }
            }
        }
    }

}