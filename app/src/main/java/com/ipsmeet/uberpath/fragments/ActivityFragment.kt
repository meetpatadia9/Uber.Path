package com.ipsmeet.uberpath.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.adapter.TransactionAdapter
import com.ipsmeet.uberpath.databinding.FragmentActivityBinding
import com.ipsmeet.uberpath.dataclass.TransactionDataClass

class ActivityFragment : Fragment() {

    lateinit var binding: FragmentActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentActivityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.layout_fragmentLoader, HomeFragment())
                .addToBackStack(null)
                .commit()
        }

        val transactions = arrayListOf<TransactionDataClass>()
        transactions.apply {
            add(TransactionDataClass(R.drawable.img_basketball, "Sports Shop", "Payment", "$15.99", "December 28"))
            add(TransactionDataClass(R.drawable.img_money_receive, "From Test User9", "Received", "$61.18", "November 28"))
            add(TransactionDataClass(R.drawable.img_money_receive, "Bank of Baroda", "Deposit", "$2,045.00", "October 28"))
            add(TransactionDataClass(R.drawable.img_send, "To Test User1", "Sent", "$986.00", "September 28"))
            add(TransactionDataClass(R.drawable.img_line, "Line premium", "Payment", "$24.00", "December 28"))
            add(TransactionDataClass(R.drawable.img_bitcoin, "Bitcoin", "Investment", "$2,550.99", "November 28"))
            add(TransactionDataClass(R.drawable.img_paypal, "Paypal", "Freelance", "$2,328.00", "October 28"))
            add(TransactionDataClass(R.drawable.img_spotify, "Spotify premium", "Payment", "$24.00", "September 28"))
        }

        binding.recyclerViewTransaction.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = TransactionAdapter(requireContext(), transactions)
        }
    }

}