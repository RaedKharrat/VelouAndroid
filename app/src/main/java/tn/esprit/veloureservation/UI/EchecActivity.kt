package tn.esprit.veloureservation.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.veloureservation.R

class EchecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.failpayment)

        val tryAgainButton: Button = findViewById(R.id.submittButton)
        val marketplaceButton: Button = findViewById(R.id.submitttButton)

        tryAgainButton.setOnClickListener {
            // Handle the "Try again !" button click
            // You can add any logic or navigation here
            navigateToMainActivity2()
        }

        marketplaceButton.setOnClickListener {
            // Handle the "Back To Market place" button click
            // You can add any logic or navigation here
            navigateToMainActivity2()
        }
    }

    private fun navigateToMainActivity2() {
        val intent = Intent(this, MainActivity2::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
