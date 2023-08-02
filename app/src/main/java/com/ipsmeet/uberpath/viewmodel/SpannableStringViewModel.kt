package com.ipsmeet.uberpath.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.activity.SignUpActivity

class SpannableStringViewModel: ViewModel() {

    //  SIGN-IN ACTIVITY
    fun doNotHaveAccount(context: Context): SpannableString {
        val spannableString = SpannableString(context.getText(R.string.txt_no_account))

        //  Create clickable part of string
        val clickablePart = object : ClickableSpan() {
            //  when user click on that particular part
            override fun onClick(widget: View) {
                context.startActivity(
                    Intent(context, SignUpActivity::class.java)
                )
            }

            //  To remove underline of Linked/Clickable part
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }

        spannableString.apply {
            //  enable clickable part
            setSpan(clickablePart, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            //  apply color to that part
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.green)),
                23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableString
    }

    //  SIGN-UP ACTIVITY
    fun titleCreateAccount(context: Context): SpannableString {
        val spannableString = SpannableString(context.getText(R.string.txt_create_account))

        spannableString.setSpan(
            //  apply color
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.green)),
            9, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

    //  SIGN-UP ACTIVITY
    fun alreadyHaveAccount(context: Activity): SpannableString {
        val spannableString = SpannableString(context.getText(R.string.txt_already_have_account))

        //  create clickable part of string
        val clickablePart = object : ClickableSpan() {
            //  when user click on that particular part
            override fun onClick(widget: View) {
                context.finish()
            }

            //  To remove underline of Linked/Clickable part
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }

        spannableString.apply {
            //  enable clickable part
            setSpan(clickablePart, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            //  apply color to that part
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.green)),
                25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableString
    }

    //  VERIFY-IDENTITY ACTIVITY
    fun sendSecurityCode(context: Context): SpannableString {
        val spannableString = SpannableString(context.getText(R.string.txt_verification_code_info))
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.green)),
            21, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

}