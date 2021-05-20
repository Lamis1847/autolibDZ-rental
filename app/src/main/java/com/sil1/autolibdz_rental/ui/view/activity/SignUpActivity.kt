package com.sil1.autolibdz_rental.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Locataire
import com.sil1.autolibdz_rental.data.model.SignUpGoogleBody
import com.sil1.autolibdz_rental.data.repositories.SignUpRepository
import com.sil1.autolibdz_rental.utils.idTokenUser
import com.sil1.autolibdz_rental.utils.utils.Companion.showPassword
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {
    var mIsShowPass = false
    var mIsShowPass2 = false
    private val RC_SIGN_IN = 9001
    lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //la création d'un nouveau user with input validation
        createButton.setOnClickListener {
            if (validateSignInInputs()) {
                if (pwd1.text.toString() == pwd2.text.toString()) {
                    var locataire = Locataire(
                        name.text.toString(),
                        lastname.text.toString(),
                        email.text.toString(),
                        pwd1.text.toString()
                    )
                    var SignUpActivity = SignUpRepository.Companion
                    SignUpActivity.createLocataire(this, locataire)
                } else {
                    Toast.makeText(applicationContext, "Not same password", Toast.LENGTH_LONG)
                        .show()
                }

            }
        }
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("648513849628-s30qhqtimiq4mclrmb6a85svvt5hk8u6.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        google_login_btn.setOnClickListener {
            signIn()
        }


        //show and hide password 1
        ivShowHidePass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(pwd1, ivShowHidePass, mIsShowPass)
        }
        showPassword(pwd1, ivShowHidePass, mIsShowPass)
        //show and hide password 2
        ivShowHidePass2.setOnClickListener {
            mIsShowPass2 = !mIsShowPass2
            showPassword(pwd2, ivShowHidePass2, mIsShowPass2)
        }
        showPassword(pwd2, ivShowHidePass2, mIsShowPass2)

    }

    //méthode pour valider les input
    fun validateSignInInputs(): Boolean {

        if (name?.text.isNullOrEmpty()) {
            name?.error = "Person Name cannot be empty."
            return false
        }
        if (lastname?.text.isNullOrEmpty()) {
            lastname?.error = "Person LastName cannot be empty."
            return false
        }
        if (email?.text.isNullOrEmpty()) {
            email?.error = "Person Email cannot be empty."
            return false
        }
        if (pwd1?.text.isNullOrEmpty()) {
            pwd1?.error = "Person Password cannot be empty."
            return false
        }
        if (pwd2?.text.isNullOrEmpty()) {
            pwd2?.error = "Person Password cannot be empty."
            return false
        }
        return true
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_SIGN_IN
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully
            idTokenUser = account?.idToken ?: ""
            println("Google ID Token " + idTokenUser)
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                }
            var token = SignUpGoogleBody(idTokenUser)
            var SignUpGoogleActivity = SignUpRepository.Companion
            SignUpGoogleActivity.createLocataireGoogle(this, token)
        } catch (e: ApiException) {
            // Sign in was unsuccessful
            println(
                "failed code= " + e.statusCode.toString()
            )
        }
    }
}
