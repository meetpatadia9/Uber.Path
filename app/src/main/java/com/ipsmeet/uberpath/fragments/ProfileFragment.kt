package com.ipsmeet.uberpath.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.activity.HomeActivity
import com.ipsmeet.uberpath.adapter.SettingListAdapter
import com.ipsmeet.uberpath.databinding.FragmentProfileBinding
import com.ipsmeet.uberpath.dataclass.ProfileListDataClass
import com.ipsmeet.uberpath.viewmodel.ProfileSettingListViewModel

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var setting: ProfileSettingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setting = ViewModelProvider(requireActivity())[ProfileSettingListViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        (activity as HomeActivity).bottomNavigationBar.menu.findItem(R.id.menu_profile).isChecked = true

        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        //  LIST-1
        val list1 = arrayListOf<ProfileListDataClass>()
        list1.apply {
            add(ProfileListDataClass(R.drawable.img_crown, "Referral Code"))
            add(ProfileListDataClass(R.drawable.img_account, "Account Info"))
            add(ProfileListDataClass(R.drawable.img_contact, "Contact List"))
            add(ProfileListDataClass(R.drawable.img_language, "Language"))
        }
        binding.recyclerViewProfile1.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = SettingListAdapter(requireContext(), list1,
                object : SettingListAdapter.OnClick {
                    override fun onClickListener(profile: ProfileListDataClass) {
                        setting.listOneOptions(requireActivity(), profile)
                    }
                })
        }

        //  LIST-2
        val list2 = arrayListOf<ProfileListDataClass>()
        list2.apply {
            add(ProfileListDataClass(R.drawable.img_setting, "General Setting"))
            add(ProfileListDataClass(R.drawable.img_lock_orange, "Change Password"))
            add(ProfileListDataClass(R.drawable.img_qr_scan, "Change Log In PIN"))
        }
        binding.recyclerViewProfile2.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = SettingListAdapter(requireContext(), list2,
                object : SettingListAdapter.OnClick {
                    override fun onClickListener(profile: ProfileListDataClass) {
                        setting.listTwoOptions(requireActivity(), profile)
                    }
                })
        }

        //  LIST-3
        val list3 = arrayListOf<ProfileListDataClass>()
        list3.apply {
            add(ProfileListDataClass(R.drawable.img_question, "FAQs"))
            add(ProfileListDataClass(R.drawable.img_star, "Rate Us"))
        }
        binding.recyclerViewProfile3.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = SettingListAdapter(requireContext(), list3,
                object : SettingListAdapter.OnClick {
                    override fun onClickListener(profile: ProfileListDataClass) {
                        setting.listThreeOptions(requireActivity(), profile)
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