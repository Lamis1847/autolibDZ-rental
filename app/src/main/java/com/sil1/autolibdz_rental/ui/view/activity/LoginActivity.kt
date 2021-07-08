package com.sil1.autolibdz_rental.ui.view.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.repositories.LogInRepository
import com.sil1.autolibdz_rental.utils.sharedPrefFile
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //status bar
        val window: Window = this@LoginActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(
            this@LoginActivity,
            R.color.palette_yellow
        )


        setContentView(R.layout.activity_login)

        start()

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
           // val intent = Intent(this, HomeActivity::class.java)
           // startActivity(intent)

        }
        createAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    fun start() {
        val sharedPref = this.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE
        )

        val con = sharedPref.getBoolean("connected",false)
        if (con){
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}