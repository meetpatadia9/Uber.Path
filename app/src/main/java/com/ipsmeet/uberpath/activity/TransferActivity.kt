package com.ipsmeet.uberpath.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.CardAdapter
import com.ipsmeet.uberpath.adapter.ContactAdapter
import com.ipsmeet.uberpath.databinding.ActivityTransferBinding
import com.ipsmeet.uberpath.dataclass.CardDataClass
import com.ipsmeet.uberpath.dataclass.ContactDataClass

class TransferActivity : AppCompatActivity() {

    lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val cards = arrayListOf<CardDataClass>()
        cards.apply {
            add(CardDataClass(R.drawable.img_card_style1))
            add(CardDataClass(R.drawable.img_card_style2))
            add(CardDataClass(R.drawable.img_card_style3))
        }

        binding.recyclerViewCards.apply {
            layoutManager = LinearLayoutManager(this@TransferActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CardAdapter(this@TransferActivity, cards)
        }

        val contacts = arrayListOf<ContactDataClass>()
        contacts.apply {
            add(ContactDataClass(R.drawable.img_linda, getString(R.string.txt_linda)))
            add(ContactDataClass(R.drawable.img_david, getString(R.string.txt_david)))
            add(ContactDataClass(R.drawable.img_tommy, getString(R.string.txt_tommy)))
            add(ContactDataClass(R.drawable.img_susan, getString(R.string.txt_susan)))
        }

        binding.recyclerViewContacts.apply {
            layoutManager = LinearLayoutManager(this@TransferActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ContactAdapter(this@TransferActivity, contacts,
                object : ContactAdapter.OnSelected {
                    override fun recipientSelection(contact: ContactDataClass) { }
                })
        }

        binding.btnContinue.setOnClickListener {
            startActivity(
                Intent(this, SendMoneyActivity::class.java)
            )
        }
    }

}