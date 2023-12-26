package tn.esprit.veloureservation.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.veloureservation.Models.ReservationResponse
import tn.esprit.veloureservation.R
import tn.esprit.veloureservation.ViewModels.ReservationViewModel

class ListReservationActivity : AppCompatActivity(), ReservationClickListener {

    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReservationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_reservation)

        reservationViewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ReservationAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        reservationViewModel.reservationResult.observe(this, Observer { reservations ->
            adapter.submitList(reservations)
        })

        // Replace "USER_ID_TO_FETCH_RESERVATIONS" with the actual user ID
        reservationViewModel.getReservationsForUser("655e87de5c69918a939e20f9")
    }

    override fun onCancelButtonClick(reservation: ReservationResponse?) {
        if (reservation != null && reservation.id != null) {
            Log.d("ReservationActivity", "Cancel button clicked for reservation ID: ${reservation.id}")
            reservationViewModel.deleteReservation(reservation.id)
        } else {
            Log.d("ReservationActivity", "Cancel button clicked, but reservation or its ID is null.")
            // Handle the case when reservation or its id is null (optional)
        }
    }

    override fun onPromoButtonClick(reservation: ReservationResponse?) {
        // Start the PromocodeActivity when the "Promo code !" button is clicked
        val intent = Intent(this@ListReservationActivity, PromocodeActivity::class.java)
        startActivity(intent)
    }
}
