package com.ipsmeet.uberpath.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.activity.EditCardActivity
import com.ipsmeet.uberpath.activity.HomeActivity
import com.ipsmeet.uberpath.databinding.FragmentMyCardBinding

class MyCardFragment : Fragment() {

    lateinit var binding: FragmentMyCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        (activity as HomeActivity).bottomNavigationBar.menu.findItem(R.id.menu_myCard).isChecked = true

        // Inflate the layout for this fragment
        binding = FragmentMyCardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        //  CARD 1
        binding.imgVCardStyle1.setOnClickListener {
            requireActivity().startActivity(
                Intent(requireContext(), EditCardActivity::class.java)
                    .putExtra("cardStyle", "cardOne")
            )
        }

        //  CARD 2
        binding.imgVCardStyle2.setOnClickListener {
            requireActivity().startActivity(
                Intent(requireContext(), EditCardActivity::class.java)
                    .putExtra("cardStyle", "cardTwo")
            )
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.apply {
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
            WindowCompat.getInsetsController(this, decorView).isAppearanceLightStatusBars = true
        }
    }

}