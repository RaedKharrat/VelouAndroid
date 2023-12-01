package tn.esprit.veloureservation

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservationpage)

        val submitButton: Button = findViewById(R.id.submitButton)
        val radioGroup: RadioGroup = findViewById(R.id.paymentMethodRadioGroup)

        // Set click listener for the submit button
        submitButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // Check which radio button is selected
                val selectedRadioButtonId = radioGroup.checkedRadioButtonId

                if (selectedRadioButtonId != -1) {
                    // At least one radio button is selected
                    val intent = when (selectedRadioButtonId) {
                        R.id.radioButtonCash -> Intent(applicationContext, PaylaterActivity::class.java)
                        R.id.radioButtonCard -> Intent(applicationContext, CardActivity::class.java)
                        else -> null
                    }

                    intent?.let {
                        // Navigate to the selected activity
                        startActivity(it)
                    }
                } else {
                    // No radio button is selected, show an alert
                    showNoRadioButtonSelectedAlert()
                }
            }
        })
    }

    private fun showNoRadioButtonSelectedAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Missed field")
            .setMessage("Please select a payment method.")
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                // You can handle the "OK" button click if needed
            })
            .show()
    }
}
