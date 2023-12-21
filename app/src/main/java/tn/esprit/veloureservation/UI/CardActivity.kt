package tn.esprit.veloureservation.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.view.CardMultilineWidget
import tn.esprit.veloureservation.Models.ReservationRequest
import tn.esprit.veloureservation.R
import tn.esprit.veloureservation.UI.ValidActivity
import tn.esprit.veloureservation.UI.EchecActivity
import tn.esprit.veloureservation.ViewModels.ReservationViewModel
import java.util.Date
import androidx.lifecycle.ViewModelProvider
import com.stripe.android.PaymentConfiguration
import androidx.appcompat.app.AlertDialog  // Make sure this import is present

class CardActivity : AppCompatActivity() {

    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var cardMultilineWidget: CardMultilineWidget

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcard)

        reservationViewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)

        val dateReservationMillis = intent.getLongExtra("dateReservation", 0L)
        val dateReservation = Date(dateReservationMillis)

        cardMultilineWidget = findViewById(R.id.cardMultilineWidget)

        val submitButton: Button = findViewById(R.id.submittButton)

        submitButton.setOnClickListener {
            // Check if all fields in the Stripe widget are valid
            if (cardMultilineWidget.validateAllFields()) {
                // Handle success
                val reservationRequest = ReservationRequest(
                    id = "",
                    dateReservation = dateReservation,
                    typePayment = "Credit Card", // You can set the payment type as needed
                    etat = false,
                    idUser = "655e87de5c69918a939e20f9", // Replace with actual user ID
                    idVelo = "655d1d936d213ea3741af704", // Replace with actual bike ID
                    promoCode = "",
                    stripeCheckoutSessionId = "" // You can leave this empty for simplicity
                )

                if (validateReservationRequest(reservationRequest)) {
                    reservationViewModel.commandeVelo(reservationRequest)

                    // Replace ValidActivity with the appropriate class for handling success
                    val intent = Intent(this@CardActivity, ValidActivity::class.java)
                    startActivity(intent)
                } else {
                    // Show an alert if any other required field is invalid
                    showFieldsEmptyAlert()
                }
            } else {
                // Navigate to EchecActivity if any field in the Stripe widget is invalid
                val intent = Intent(this@CardActivity, EchecActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateReservationRequest(reservationRequest: ReservationRequest): Boolean {
        // Perform validation logic here
        // For example, check if required fields are not empty
        return !reservationRequest.idUser.isNullOrEmpty() &&
                !reservationRequest.idVelo.isNullOrEmpty() &&
                !reservationRequest.typePayment.isNullOrEmpty() &&
                reservationRequest.dateReservation != null
    }

    private fun showFieldsEmptyAlert() {
        // You can customize this alert message as needed
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Invalid Fields")
            .setMessage("Please fill in all the required fields correctly.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
