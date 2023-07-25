package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spannableString = SpannableString(getText(R.string.txt_no_account))

        val clickablePart = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(
                    Intent(this@SignInActivity, SignUpActivity::class.java)
                )
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }

        spannableString.apply {
            setSpan(clickablePart, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this@SignInActivity, R.color.green)),
                23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.txtNoAccount.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

        binding.btnSignIn.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java)
            )
            finish()
        }

        binding.txtForgotPassword.setOnClickListener {
            startActivity(
                Intent(this, PasswordRecoveryActivity::class.java)
            )
        }
    }
}