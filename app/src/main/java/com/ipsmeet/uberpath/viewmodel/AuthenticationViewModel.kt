package com.ipsmeet.uberpath.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ipsmeet.uberpath.R

class AuthenticationViewModel: ViewModel() {

    private lateinit var googleSignInClient: GoogleSignInClient

    fun initializeGoogleAuth(context: Context): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOption)
        return googleSignInClient
    }

}