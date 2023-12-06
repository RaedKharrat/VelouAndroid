package tn.esprit.veloureservation.UI

import android.content.DialogInterface
import android.content.Intent  // Import Intent class for navigation
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tn.esprit.veloureservation.Models.ReservationRequest
import tn.esprit.veloureservation.R
import tn.esprit.veloureservation.UI.PaylaterActivity
import tn.esprit.veloureservation.UI.ValidActivity
import tn.esprit.veloureservation.ViewModels.ReservationViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var datePicker: DatePicker
    private lateinit var paymentMethodRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservationpage)

        reservationViewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)
        datePicker = findViewById(R.id.datePicker)
        paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup)
        val submitButton: Button = findViewById(R.id.submitButton)

        submitButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val dateReservation = getSelectedDate()
                val selectedPaymentMethod = getSelectedPaymentMethod()

                if (dateReservation != null && selectedPaymentMethod != null) {
                    handleReservationSubmission(dateReservation, selectedPaymentMethod)
                } else {
                    showNoRadioButtonSelectedAlert()
                }
            }
        })
    }

    private fun getSelectedDate(): Calendar? {
        val selectedYear = datePicker.year
        val selectedMonth = datePicker.month
        val selectedDay = datePicker.dayOfMonth

        val calendar = Calendar.getInstance()
        calendar.set(selectedYear, selectedMonth, selectedDay)

        return calendar
    }

    private fun getSelectedPaymentMethod(): String? {
        val selectedRadioButtonId = paymentMethodRadioGroup.checkedRadioButtonId

        return when (selectedRadioButtonId) {
            R.id.radioButtonCash -> "pay Later"
            R.id.radioButtonCard -> "Credit Card"
            else -> null
        }
    }

    private fun handleReservationSubmission(dateReservation: Calendar, paymentMethod: String) {
        val etat = false // Set your etat value
        val idUser = "655e87de5c69918a939e20f9" // Set your idUser value
        val idVelo = "655d1d936d213ea3741af704" // Set your idVelo value
        val promoCode = "" // Set your promoCode value
        val stripeCheckoutSessionId = "" // Set your stripeCheckoutSessionId value

        val reservationRequest = ReservationRequest(
            dateReservation = dateReservation.time,
            typePayment = paymentMethod,
            etat = etat,
            idUser = idUser,
            idVelo = idVelo,
            promoCode = promoCode,
            stripeCheckoutSessionId = stripeCheckoutSessionId
        )

        reservationViewModel.commandeVelo(reservationRequest)

        // Navigate to the appropriate activity based on the payment method
        when (paymentMethod) {
            "Credit Card" -> {
                val intent = Intent(this, ValidActivity::class.java)
                startActivity(intent)
            }
            "pay Later" -> {
                val intent = Intent(this, PaylaterActivity::class.java)
                startActivity(intent)
            }
            else -> {
                // Handle other payment methods if needed
            }
        }

        // Additional logic if needed
        // You can use dateReservation and paymentMethod here or perform other actions
        Toast.makeText(this, "Reservation submitted successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showNoRadioButtonSelectedAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Missed field")
            .setMessage("Please select a payment method and reservation date.")
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                // You can handle the "OK" button click if needed
            })
            .show()
    }
}
