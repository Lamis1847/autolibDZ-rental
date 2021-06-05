package com.sil1.autolibdz_rental.ui.view.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.repositories.LogInRepository
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        connectButton.setOnClickListener {

            val email = Email.text.toString()
            val password = Pass.text.toString()

            if (email.isEmpty()) {
                Email.error = "Email Required"
                Email.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Pass.error = "Password Required"
                Pass.requestFocus()
                return@setOnClickListener
            }

            var loginActivity = LogInRepository.Companion
           loginActivity.login(this, email, password)

        }
        createAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}