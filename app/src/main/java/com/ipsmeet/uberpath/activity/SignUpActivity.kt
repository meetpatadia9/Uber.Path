package com.ipsmeet.uberpath.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ipsmeet.uberpath.databinding.ActivitySignUpBinding
import com.ipsmeet.uberpath.viewmodel.AuthenticationViewModel
import com.ipsmeet.uberpath.viewmodel.SpannableStringViewModel
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

    private lateinit var spannableString: SpannableStringViewModel
    private lateinit var authViewModel: AuthenticationViewModel
    private lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        spannableString = ViewModelProvider(this)[SpannableStringViewModel::class.java]

        gsc = authViewModel.initializeGoogleAuth(this)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.txtCreateAccount.text = spannableString.titleCreateAccount(this)

        binding.edtxtFullName.addTextChangedListener(textWatcher)
        binding.edtxtEmail.addTextChangedListener(textWatcher)

        binding.btnSignUp.setOnClickListener {
            updateUI()
        }

        //  GOOGLE LOGIN
        binding.btnGoogleLogin.setOnClickListener {
            startGoogleAuth()
        }

        //  APPLE LOGIN
        binding.btnAppleLogin.setOnClickListener {
            Toast.makeText(this, "Not available for this device.", Toast.LENGTH_SHORT).show()
        }

        binding.txtAlreadyAccount.apply {
            text = spannableString.alreadyHaveAccount(this@SignUpActivity)
            movementMethod = LinkMovementMethod.getInstance()   // handle clickable link
            highlightColor = Color.TRANSPARENT  // won't show highlighting color, when the link is pressed
        }
    }

    private fun startGoogleAuth() {
        val authIntent = gsc.signInIntent
        googleAuthLauncher.launch(authIntent)
    }

    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            handleResult(
                GoogleSignIn.getSignedInAccountFromIntent(it.data)
            )
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            //  Sign-in-with-credential
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        updateUI()
                    } else {
                        Log.e("Auth FAIL", it.exception!!.message.toString())
                    }
                }
        }
    }

    private fun updateUI() {
        startActivity(
            Intent(this, CountryActivity::class.java)
        )
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