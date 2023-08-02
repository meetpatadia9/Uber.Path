package com.ipsmeet.uberpath.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ipsmeet.uberpath.databinding.ActivitySignInBinding
import com.ipsmeet.uberpath.viewmodel.AuthenticationViewModel
import com.ipsmeet.uberpath.viewmodel.SpannableStringViewModel
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

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var spannableString: SpannableStringViewModel
    private lateinit var authViewModel: AuthenticationViewModel
    private lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Initialize view-models
        authViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        spannableString = ViewModelProvider(this)[SpannableStringViewModel::class.java]

        //  Initialize google-authentication
        gsc = authViewModel.initializeGoogleAuth(this)

        //  Initialize shared-preference
        sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE)

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
            /*if (binding.edtxtPassword.text.toString().trim() == "") {
                Toast.makeText(this, "Enter password.", Toast.LENGTH_SHORT).show()
            }*/
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
                /*finish()*/
            }
        }

        //  GOOGLE SIGN-IN
        binding.btnGoogleLogin.setOnClickListener {
            startGoogleAuth()
        }

        //  APPLE SIGN-IN
        binding.btnAppleLogin.setOnClickListener {
            Toast.makeText(this, "Not available for this device.", Toast.LENGTH_SHORT).show()
        }

        //  Apply design to text-view
        binding.txtNoAccount.apply {
            text = spannableString.doNotHaveAccount(this@SignInActivity)
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
            if (account != null) {
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
            else {
                Log.e("Task FAIL", "handleResult: ${ task.exception!!.message.toString() }")
            }
        }
    }

    private fun updateUI() {
        startActivity(
            Intent(this, HomeActivity::class.java)
        )
        finish()
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

    override fun onStart() {
        super.onStart()
        val isLogged = sharedPreferences.getBoolean("isLogged", false)
        if (isLogged || FirebaseAuth.getInstance().currentUser != null) {
            updateUI()
        }
    }

}