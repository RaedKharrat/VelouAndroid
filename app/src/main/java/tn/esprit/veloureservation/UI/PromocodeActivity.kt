package tn.esprit.veloureservation.UI

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.veloureservation.R

class PromocodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.codepromo)

        val textField: EditText = findViewById(R.id.textField)
        textField.inputType = InputType.TYPE_CLASS_NUMBER
        textField.filters = arrayOf(InputFilter.LengthFilter(6))

        // Your other code...
    }
}
