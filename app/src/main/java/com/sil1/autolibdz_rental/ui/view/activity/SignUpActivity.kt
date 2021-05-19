package com.sil1.autolibdz_rental.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Locataire
import com.sil1.autolibdz_rental.data.repositories.SignUpRepository
import com.sil1.autolibdz_rental.utils.utils.Companion.showPassword
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    var mIsShowPass = false
    var mIsShowPass2 = false
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
}
