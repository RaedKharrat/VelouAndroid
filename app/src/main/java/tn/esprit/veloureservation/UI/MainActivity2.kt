package tn.esprit.veloureservation.UI

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import tn.esprit.veloureservation.Models.ReservationRequest
import tn.esprit.veloureservation.R
import tn.esprit.veloureservation.UI.CardActivity
import tn.esprit.veloureservation.UI.PaylaterActivity
import tn.esprit.veloureservation.ViewModels.ReservationViewModel
import java.util.Calendar

class MainActivity2 : AppCompatActivity() {

    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var datePicker: DatePicker
    private lateinit var paymentMethodRadioGroup: RadioGroup
    private lateinit var submitButton: Button
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var totalPriceTextView1: TextView
    private lateinit var totalPriceTextView2: TextView
    private lateinit var totalPriceTextView3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservationpage)

        reservationViewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)
        datePicker = findViewById(R.id.datePicker)
        paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup)
        submitButton = findViewById(R.id.submitButton)
        nestedScrollView = findViewById(R.id.nestedScrollView)
        totalPriceTextView1 = findViewById(R.id.totalPriceTextView1)
        totalPriceTextView2 = findViewById(R.id.totalPriceTextView2)
        totalPriceTextView3 = findViewById(R.id.totalPriceTextView3)

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            // Hide TextViews when user scrolls down
            if (scrollY > 0) {
                totalPriceTextView1.visibility = View.GONE
                totalPriceTextView2.visibility = View.GONE
                totalPriceTextView3.visibility = View.GONE
            } else {
                totalPriceTextView1.visibility = View.VISIBLE
                totalPriceTextView2.visibility = View.VISIBLE
                totalPriceTextView3.visibility = View.VISIBLE
            }
        })

        submitButton.setOnClickListener {
            val dateReservation = getSelectedDate()
            val selectedPaymentMethod = getSelectedPaymentMethod()

            if (dateReservation != null && selectedPaymentMethod != null) {
                handleReservationSubmission(dateReservation, selectedPaymentMethod)
            } else {
                showNoRadioButtonSelectedAlert()
            }
        }
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
        when (paymentMethod) {
            "Credit Card" -> {
                val intent = Intent(this, CardActivity::class.java)
                intent.putExtra("dateReservation", dateReservation.timeInMillis)
                startActivity(intent)
            }
            "pay Later" -> {
                val etat = false
                val idUser = "655e87de5c69918a939e20f9"
                val idVelo = "655d1d936d213ea3741af704"
                val promoCode = ""
                val stripeCheckoutSessionId = ""

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

                val intent = Intent(this, PaylaterActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                // Handle other payment methods if needed
                Toast.makeText(this, "Reservation submitted successfully", Toast.LENGTH_SHORT).show()
            }
        }
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
