package com.ipsmeet.uberpath.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.activity.MapActivity
import com.ipsmeet.uberpath.adapter.SettingListAdapter
import com.ipsmeet.uberpath.databinding.FragmentMoreBinding
import com.ipsmeet.uberpath.dataclass.ProfileListDataClass

class MoreFragment : Fragment() {

    lateinit var binding: FragmentMoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val moreSettings = arrayListOf<ProfileListDataClass>()
        moreSettings.apply {
            add(ProfileListDataClass(R.drawable.img_bank, "Find ATM"))
        }

        binding.recyclerViewMoreSetting.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = SettingListAdapter(requireContext(), moreSettings,
                object : SettingListAdapter.OnClick {
                    override fun onClickListener(profile: ProfileListDataClass) {
                        if (profile.text == "Find ATM") {
                            startActivity(
                                Intent(requireContext(), MapActivity::class.java)
                            )
                        }
                    }
                })
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