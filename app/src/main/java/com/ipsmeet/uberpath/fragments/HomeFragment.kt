package com.ipsmeet.uberpath.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.activity.HistoryActivity
import com.ipsmeet.uberpath.activity.TopUpActivity
import com.ipsmeet.uberpath.activity.TransferActivity
import com.ipsmeet.uberpath.activity.WithdrawActivity
import com.ipsmeet.uberpath.adapter.TransactionAdapter
import com.ipsmeet.uberpath.databinding.FragmentHomeBinding
import com.ipsmeet.uberpath.dataclass.TransactionDataClass

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.green)
        sharedPreferences = requireContext().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtUserName.text = sharedPreferences.getString("usersName", requireContext().getString(R.string.txt_tommy))

        //  TOP-UP
        binding.layoutDeposit.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), TopUpActivity::class.java)
            )
        }

        //  TRANSFERS
        binding.layoutTransfers.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), TransferActivity::class.java)
            )
        }

        //  WITHDRAW
        binding.layoutWithdraw.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), WithdrawActivity::class.java)
            )
        }

        //  HISTORY TRANSACTION
        binding.txtAllTransaction.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), HistoryActivity::class.java)
            )
        }

        val transactionList = arrayListOf<TransactionDataClass>()
        transactionList.apply {
            add(TransactionDataClass(R.drawable.img_basketball, "Sports Shop", "Payment", "$15.99", "December 28"))
            add(TransactionDataClass(R.drawable.img_money_receive, "From Test User9", "Received", "$61.18", "November 28"))
            add(TransactionDataClass(R.drawable.img_money_receive, "Bank of Baroda", "Deposit", "$2,045.00", "October 28"))
            add(TransactionDataClass(R.drawable.img_send, "To Test User1", "Sent", "$986.00", "September 28"))
        }

        binding.recyclerViewHomeTransaction.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = TransactionAdapter(requireActivity(), transactionList)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

}