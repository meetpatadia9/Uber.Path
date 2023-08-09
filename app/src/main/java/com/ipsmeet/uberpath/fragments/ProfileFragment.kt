package com.ipsmeet.uberpath.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.activity.AccountInfoActivity
import com.ipsmeet.uberpath.activity.ContactsActivity
import com.ipsmeet.uberpath.activity.FAQActivity
import com.ipsmeet.uberpath.activity.GeneralSettingActivity
import com.ipsmeet.uberpath.activity.ReferralCodeActivity
import com.ipsmeet.uberpath.activity.SelectLanguageActivity
import com.ipsmeet.uberpath.adapter.SettingListAdapter
import com.ipsmeet.uberpath.databinding.FragmentProfileBinding
import com.ipsmeet.uberpath.dataclass.ProfileListDataClass

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                        listOneOptions(profile)
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
                        listTwoOptions(profile)
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
                        listThreeOptions(profile)
                    }
                })
        }
    }

    private fun listOneOptions(profile: ProfileListDataClass) {
        when (profile.text) {
            "Referral Code" -> {
                requireContext().startActivity(
                    Intent(requireContext(), ReferralCodeActivity::class.java)
                )
            }

            "Contact List" -> {
                requireContext().startActivity(
                    Intent(requireContext(), ContactsActivity::class.java)
                )
            }

            "Account Info" -> {
                requireContext().startActivity(
                    Intent(requireContext(), AccountInfoActivity::class.java)
                )
            }

            "Language" -> {
                requireContext().startActivity(
                    Intent(requireContext(), SelectLanguageActivity::class.java)
                )
            }
        }
    }

    private fun listTwoOptions(profile: ProfileListDataClass) {
        if (profile.text == "General Setting") {
            requireContext().startActivity(
                Intent(requireContext(), GeneralSettingActivity::class.java)
            )
        }
    }

    private fun listThreeOptions(profile: ProfileListDataClass) {
        if (profile.text == "FAQs") {
            requireContext().startActivity(
                Intent(requireContext(), FAQActivity::class.java)
            )
        }
    }

}