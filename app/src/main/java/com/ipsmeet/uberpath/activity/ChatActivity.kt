package com.ipsmeet.uberpath.activity

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.ChatAdapter
import com.ipsmeet.uberpath.databinding.ActivityChatBinding
import com.ipsmeet.uberpath.dataclass.ChatDataClass

class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding
    private var isAttachmentsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val chats = arrayListOf<ChatDataClass>()
        chats.apply {
            add(ChatDataClass("Bot", "Hello! my name is Co before we start, what is your name?"))
            add(ChatDataClass("User", "Tommy Jason"))
            add(ChatDataClass("Bot", "Hello! Tommy \uD83D\uDC4B. I can converse in your preferred language. How may I help you today?"))
            add(ChatDataClass("User", "How to create a Uber.Path account?"))
        }
        binding.recyclerViewChat.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ChatAdapter(this@ChatActivity, chats)
        }

        binding.edtxtMsg.addTextChangedListener(textWatcher)

        binding.imgVLink.setOnClickListener {
            if (isAttachmentsVisible) {
                hideAttachments()
                isAttachmentsVisible = false
            } else {
                showAttachments()
                isAttachmentsVisible = true
            }
        }
    }

    //  TEXT-WATCHER
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        override fun afterTextChanged(s: Editable?) {
            if (binding.edtxtMsg.text.toString().trim() != "") {
                binding.edtxtMsg.setTypeface(null, Typeface.BOLD)
            } else {
                binding.edtxtMsg.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        window.apply {
            statusBarColor = ContextCompat.getColor(this@ChatActivity, R.color.blue)
            WindowCompat.getInsetsController(window, decorView).isAppearanceLightStatusBars = false
        }
    }

    private fun showAttachments() {
        binding.layoutAttachments.visibility = View.VISIBLE
        Glide.with(this)
            .load(R.drawable.img_close)
            .into(binding.imgVLink)
        binding.imgVLink.setBackgroundResource(R.drawable.shape_btn_green_filled)
    }

    private fun hideAttachments() {
        binding.layoutAttachments.visibility = View.GONE
        Glide.with(this)
            .load(R.drawable.img_link)
            .into(binding.imgVLink)
        binding.imgVLink.setBackgroundResource(R.drawable.shape_edittext)
    }

}