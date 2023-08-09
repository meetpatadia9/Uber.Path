package com.ipsmeet.uberpath.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.ipsmeet.uberpath.activity.AccountInfoActivity
import com.ipsmeet.uberpath.activity.ContactsActivity
import com.ipsmeet.uberpath.activity.FAQActivity
import com.ipsmeet.uberpath.activity.GeneralSettingActivity
import com.ipsmeet.uberpath.activity.ReferralCodeActivity
import com.ipsmeet.uberpath.activity.SelectLanguageActivity
import com.ipsmeet.uberpath.dataclass.ProfileListDataClass

class ProfileSettingListViewModel: ViewModel() {

    fun listOneOptions(context: Context, profile: ProfileListDataClass) {
        when (profile.text) {
            "Referral Code" -> {
                context.startActivity(
                    Intent(context, ReferralCodeActivity::class.java)
                )
            }

            "Contact List" -> {
                context.startActivity(
                    Intent(context, ContactsActivity::class.java)
                )
            }

            "Account Info" -> {
                context.startActivity(
                    Intent(context, AccountInfoActivity::class.java)
                )
            }

            "Language" -> {
                context.startActivity(
                    Intent(context, SelectLanguageActivity::class.java)
                )
            }
        }
    }

    fun listTwoOptions(context: Context, profile: ProfileListDataClass) {
        if (profile.text == "General Setting") {
            context.startActivity(
                Intent(context, GeneralSettingActivity::class.java)
            )
        }
    }

    fun listThreeOptions(context: Context, profile: ProfileListDataClass) {
        if (profile.text == "FAQs") {
            context.startActivity(
                Intent(context, FAQActivity::class.java)
            )
        }
    }

}