package tn.esprit.veloureservation.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.veloureservation.Models.ReservationRequest
import tn.esprit.veloureservation.R
import tn.esprit.veloureservation.ViewModels.ReservationViewModel
import java.util.Date
import androidx.lifecycle.ViewModelProvider

class CardActivity : AppCompatActivity() {

    private lateinit var reservationViewModel: ReservationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcard)

        reservationViewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)

        val dateReservationMillis = intent.getLongExtra("dateReservation", 0L)
        val dateReservation = Date(dateReservationMillis)

        val submitButton: Button = findViewById(R.id.submittButton)
        val cardNumberEditText: EditText = findViewById(R.id.textField)
        val cardHolderEditText: EditText = findViewById(R.id.textField2)
        val cvvEditText: EditText = findViewById(R.id.textField3)
        val expirationDateEditText: EditText = findViewById(R.id.textField4)
        val saveCardCheckBox: CheckBox = findViewById(R.id.toggleSwitch)

        submitButton.setOnClickListener {
            if (validateFields(
                    cardNumberEditText,
                    cardHolderEditText,
                    cvvEditText,
                    expirationDateEditText
                )
            ) {
                val reservationRequest = ReservationRequest(
                    dateReservation = dateReservation,
                    typePayment = "Credit Card",
                    etat = false,
                    idUser = "655e87de5c69918a939e20f9",
                    idVelo = "655d1d936d213ea3741af704",
                    promoCode = "",
                    stripeCheckoutSessionId = ""
                )

                reservationViewModel.commandeVelo(reservationRequest)

                val intent = Intent(this, ValidActivity::class.java)
                startActivity(intent)
            } else {
                showFieldsEmptyAlert()
            }
        }
    }

    private fun validateFields(
        cardNumberEditText: EditText,
        cardHolderEditText: EditText,
        cvvEditText: EditText,
        expirationDateEditText: EditText
    ): Boolean {
        val cardNumber = cardNumberEditText.text.toString()
        val cvv = cvvEditText.text.toString()

        return cardNumber.length == 16 && cvv.length == 3 &&
                cardHolderEditText.text.isNotEmpty() &&
                expirationDateEditText.text.isNotEmpty()
    }

    private fun showFieldsEmptyAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Invalid Fields")
            .setMessage("Please fill in all the required fields correctly.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
