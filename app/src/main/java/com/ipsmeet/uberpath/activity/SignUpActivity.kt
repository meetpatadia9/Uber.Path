package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivitySignUpBinding
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z\\d+._%\\-]{1,256}" +            //  \\d == 0 to 9
                "@" +
                "[a-zA-Z\\d][a-zA-Z\\d\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z\\d][a-zA-Z\\d\\-]{0,25}" +
                ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  BACK BUTTON
        binding.btnBack.setOnClickListener {
            finish()
        }

        //  STRING-1
        val spannableString1 = SpannableString(getText(R.string.txt_create_account))
        //  apply color
        spannableString1.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this@SignUpActivity, R.color.green)),
            9, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //  apply design to text-view
        binding.txtCreateAccount.text = spannableString1

        binding.edtxtFullName.addTextChangedListener(textWatcher)
        binding.edtxtEmail.addTextChangedListener(textWatcher)

        //  SIGN-UP BUTTON
        binding.btnSignUp.setOnClickListener {
            startActivity(
                Intent(this, CountryActivity::class.java)
            )
        }

        //  STRING-2
        val spannableString2 = SpannableString(getText(R.string.txt_already_have_account))
        //  create clickable part of string
        val clickablePart = object : ClickableSpan() {
            //  when user click on that particular part
            override fun onClick(widget: View) {
                finish()
            }
            //  To remove underline of Linked/Clickable part
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }
        //  apply all the properties to string
        spannableString2.apply {
            //  enable clickable part
            setSpan(clickablePart, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            //  apply color to that part
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this@SignUpActivity, R.color.green)),
                25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        //  apply design to text-view
        binding.txtAlreadyAccount.apply {
            text = spannableString2
            movementMethod = LinkMovementMethod.getInstance()   // handle clickable link
            highlightColor = Color.TRANSPARENT  // won't show highlighting color, when the link is pressed
        }
    }

    //  TEXT-WATCHER
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        override fun afterTextChanged(s: Editable?) {
            when (s.hashCode()) {
                binding.edtxtFullName.text.hashCode() -> {
                    if (binding.edtxtFullName.text.toString().trim() != "") {
                        binding.edtxtFullName.setTypeface(null, Typeface.BOLD)
                    } else {
                        binding.edtxtFullName.setTypeface(null, Typeface.NORMAL)
                    }
                }

                binding.edtxtEmail.text.hashCode() -> {
                    if (binding.edtxtEmail.text.toString().trim() != "") {
                        binding.edtxtEmail.setTypeface(null, Typeface.BOLD)
                    } else {
                        binding.edtxtEmail.setTypeface(null, Typeface.NORMAL)
                    }
                }
            }
        }
    }

    //  CHECKING ENTERED EMAIL VALIDITY
    private fun isValidString(str: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }
}