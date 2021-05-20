package com.sil1.autolibdz_rental.ui.view.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sil1.autolibdz_rental.R
import kotlinx.android.synthetic.main.activity_profil_locataire.*
import java.util.*

class ProfilLocataireActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_locataire)
        nameInput.setText("Benaissa")
        lastNameInput.setText("Yacine")
        emailInput.setText("hb_chergui@esi.dz")
        phoneNumberInput.setText("0797816168")

        //DatePicker
        val c = Calendar.getInstance()
        val year =c.get(Calendar.YEAR)
        val month =c.get(Calendar.MONTH)
        val day =c.get(Calendar.DAY_OF_MONTH)


        birthDay.setOnClickListener {
            val dpd= DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    birth.text = "" + myear + "/" + mmonth + "/" + mdayOfMonth
                    print(birth.text.toString())
                },
                year,
                month,
                day
            )
            dpd.show()

        }
    }
}