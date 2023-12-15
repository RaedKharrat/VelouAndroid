package tn.esprit.veloureservation.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.model.CardParams
import com.stripe.android.model.Token
import com.stripe.android.utils.CardUtils
import com.stripe.android.view.CardMultilineWidget
import tn.esprit.veloureservation.Models.ReservationRequest
import tn.esprit.veloureservation.R
import tn.esprit.veloureservation.UI.ValidActivity
import tn.esprit.veloureservation.ViewModels.ReservationViewModel
import java.util.Date
import androidx.lifecycle.ViewModelProvider

class CardActivity : AppCompatActivity() {

    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var stripe: com.stripe.android.Stripe
    private lateinit var cardMultilineWidget: CardMultilineWidget
    private lateinit var cardHolderEditText: EditText
    private lateinit var cvvEditText: EditText
    private lateinit var expirationDateEditText: EditText
    private lateinit var saveCardCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcard)

        reservationViewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)

        val dateReservationMillis = intent.getLongExtra("dateReservation", 0L)
        val dateReservation = Date(dateReservationMillis)

        // Initialize Stripe with your publishable key
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51OCrioL5tL8k5Xpx1oqocFMpIrM3p7sMKIb2GyOdk3H4R54fMKWsMkpg9DSjFzHqutAdYafEdxGbK6sfCXZsEgea006Vuc77WL"
        ) // Replace with your actual publishable key
        stripe = com.stripe.android.Stripe(
            this,
            PaymentConfiguration.getInstance(this).publishableKey
        )

        cardMultilineWidget = findViewById(R.id.cardMultilineWidget)
        cardHolderEditText = findViewById(R.id.textField2)
        cvvEditText = findViewById(R.id.textField3)
        expirationDateEditText = findViewById(R.id.textField4)
        saveCardCheckBox = findViewById(R.id.toggleSwitch)

        val submitButton: Button = findViewById(R.id.submittButton)

        submitButton.setOnClickListener {
            val cardParams = createCardParams()

            if (validateFields(cardParams)) {
                // Create a token using the CardParams
                stripe.createCardToken(
                    cardParams,
                    null,
                    null,
                    object : ApiResultCallback<Token> {
                        override fun onSuccess(result: Token) {
                            // Handle success
                            val reservationRequest = ReservationRequest(
                                dateReservation = dateReservation,
                                typePayment = "Credit Card",
                                etat = false,
                                idUser = "655e87de5c69918a939e20f9", // Replace with actual user ID
                                idVelo = "655d1d936d213ea3741af704", // Replace with actual bike ID
                                promoCode = "",
                                stripeCheckoutSessionId = result.id
                            )

                            reservationViewModel.commandeVelo(reservationRequest)

                            // Replace ValidActivity with the appropriate class for handling success
                            val intent = Intent(this@CardActivity, ValidActivity::class.java)
                            startActivity(intent)
                        }

                        override fun onError(e: Exception) {
                            // Handle error
                            showFieldsEmptyAlert()
                        }
                    }
                )
            } else {
                showFieldsEmptyAlert()
            }
        }
    }

    private fun createCardParams(): CardParams {
        val cardParams = cardMultilineWidget.cardParams
        return cardParams ?: CardParams.createCardParams(
            cardMultilineWidget.card?.number.orEmpty(),
            cardMultilineWidget.card?.expMonth ?: 0,
            cardMultilineWidget.card?.expYear ?: 0,
            cvvEditText.text.toString()
        )
    }

    private fun validateFields(cardParams: CardParams): Boolean {
        // Add additional validation if needed
        return CardUtils.getPossibleCardType(cardParams.cardNumber) != null
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
