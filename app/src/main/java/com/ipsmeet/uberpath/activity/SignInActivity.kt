package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivitySignInBinding
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

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
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  BACK BUTTON
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.edtxtEmail.addTextChangedListener(textWatcher)

        //  FORGOT PASSWORD
        binding.txtForgotPassword.setOnClickListener {
            startActivity(
                Intent(this, PasswordRecoveryActivity::class.java)
                    .putExtra("email", binding.edtxtEmail.text.toString().trim())
            )
        }

        //  SIGN-IN BUTTON
        binding.btnSignIn.setOnClickListener {
            if (binding.edtxtEmail.text.toString().trim() == "") {
                Toast.makeText(this, "Enter email.", Toast.LENGTH_SHORT).show()
            }
            if (binding.edtxtPassword.text.toString().trim() == "") {
                Toast.makeText(this, "Enter password.", Toast.LENGTH_SHORT).show()
            }
            if (!isValidString(binding.edtxtEmail.text.toString())) {
                Toast.makeText(this, "Invalid email. Enter proper email format!", Toast.LENGTH_SHORT).show()
            }
            /*else if (binding.edtxtPassword.text.toString().trim().length >= 8) {
                Toast.makeText(this, "Password length must be greater than 8 characters.", Toast.LENGTH_SHORT).show()
            }*/
            else {
                startActivity(
                    Intent(this, OTPActivity::class.java)
                        .putExtra("email", binding.edtxtEmail.text.toString().trim())
                )
                finish()
            }
        }

        //  GOOGLE SIGN-IN
        binding.btnGoogleLogin.setOnClickListener {
            Toast.makeText(this, "Continue with Google", Toast.LENGTH_SHORT).show()
        }

        //  APPLE SIGN-IN
        binding.btnAppleLogin.setOnClickListener {
            Toast.makeText(this, "Continue with Apple ID", Toast.LENGTH_SHORT).show()
        }

        //  String
        val spannableString = SpannableString(getText(R.string.txt_no_account))
        //  Create clickable part of string
        val clickablePart = object : ClickableSpan() {
            //  when user click on that particular part
            override fun onClick(widget: View) {
                startActivity(
                    Intent(this@SignInActivity, SignUpActivity::class.java)
                )
            }

            //  To remove underline of Linked/Clickable part
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }

        //  Apply all the properties to string
        spannableString.apply {
            //  enable clickable part
            setSpan(clickablePart, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            //  apply color to that part
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this@SignInActivity, R.color.green)),
                23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        //  Apply design to text-view
        binding.txtNoAccount.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()   // handle clickable link
            highlightColor = Color.TRANSPARENT  // won't show highlighting color, when the link is pressed
        }
    }

    //  TEXT-WATCHER
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        override fun afterTextChanged(s: Editable?) {
            if (binding.edtxtEmail.text.toString().trim() != "") {
                binding.edtxtEmail.setTypeface(null, Typeface.BOLD)
            } else {
                binding.edtxtEmail.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    //  CHECKING ENTERED EMAIL VALIDITY
    private fun isValidString(str: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

}