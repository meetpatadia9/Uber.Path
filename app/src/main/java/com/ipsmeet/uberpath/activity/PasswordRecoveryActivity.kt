package com.ipsmeet.uberpath.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.ipsmeet.uberpath.databinding.ActivityPasswordRecoveryBinding
import java.util.regex.Pattern

class PasswordRecoveryActivity : AppCompatActivity() {

    lateinit var binding: ActivityPasswordRecoveryBinding

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
        binding = ActivityPasswordRecoveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userEmail = intent.getStringExtra("email")
        if (userEmail != "") {
            binding.edtxtEmail.setTypeface(null, Typeface.BOLD)
            binding.edtxtEmail.setText(userEmail)
        } else {
            binding.edtxtEmail.setTypeface(null, Typeface.NORMAL)
        }

        binding.edtxtEmail.addTextChangedListener(textWatcher)

        binding.btnSendEmail.setOnClickListener {
            if (isValidString(binding.edtxtEmail.text.toString())) {
                startActivity(
                    Intent(this, VerifyIdentityActivity::class.java)
                        .putExtra("email", binding.edtxtEmail.text.toString().trim())
                )
            }
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