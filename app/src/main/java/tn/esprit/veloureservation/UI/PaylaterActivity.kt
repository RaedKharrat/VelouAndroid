package tn.esprit.veloureservation.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import tn.esprit.veloureservation.R

class PaylaterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paylaterview)
         val promoCodeButton: Button = findViewById(R.id.promocodeButton)
        promoCodeButton.setOnClickListener {
                   // Navigate to PromoCodeActivity
                   val intent = Intent(this, PromocodeActivity::class.java)
                   startActivity(intent)
               }
    }
}
