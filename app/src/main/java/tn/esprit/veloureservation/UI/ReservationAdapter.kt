package tn.esprit.veloureservation.UI

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.veloureservation.Models.ReservationResponse
import tn.esprit.veloureservation.R

interface ReservationClickListener {
    fun onCancelButtonClick(reservation: ReservationResponse?)
    fun onPromoButtonClick(reservation: ReservationResponse?)
}

class ReservationAdapter(private val clickListener: ReservationClickListener) :
    ListAdapter<ReservationResponse, ReservationAdapter.ReservationViewHolder>(DIFF_CALLBACK) {

    private val currentDate: Long = System.currentTimeMillis()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = getItem(position)
        Log.d("ReservationAdapter", "Binding item at position $position: $reservation")
        holder.bind(reservation, currentDate, clickListener)
    }

    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val typePaymentTextView: TextView = itemView.findViewById(R.id.typePaymentTextView)
        private val cancelButton: Button = itemView.findViewById(R.id.submittButton)
        private val promoButton: Button = itemView.findViewById(R.id.submittButtonn)

        fun bind(reservation: ReservationResponse?, currentDate: Long, clickListener: ReservationClickListener) {
            Log.d("ReservationViewHolder", "Binding reservation: $reservation")

            dateTextView.text = "Date : \n ${reservation?.dateReservation}\n"
            typePaymentTextView.text = "Payment Methode :\n ${reservation?.typePayment}"

            val reservationDate = reservation?.dateReservation?.time ?: 0L

            if (reservation != null && reservationDate > currentDate) {
                Log.d("ReservationViewHolder", "Show cancel button for reservation: $reservation")
                cancelButton.visibility = View.VISIBLE
                promoButton.visibility = View.VISIBLE
                cancelButton.setOnClickListener {
                    clickListener.onCancelButtonClick(reservation)
                }
                promoButton.setOnClickListener {
                    clickListener.onPromoButtonClick(reservation)
                }
            } else {
                Log.d("ReservationViewHolder", "Hide buttons for reservation: $reservation")
                cancelButton.visibility = View.GONE
                promoButton.visibility = View.GONE
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReservationResponse>() {
            override fun areItemsTheSame(oldItem: ReservationResponse, newItem: ReservationResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReservationResponse, newItem: ReservationResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}
