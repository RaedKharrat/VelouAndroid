package tn.esprit.veloureservation.Models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ReservationRequest(
    @SerializedName("_id")
    val id: String?,
    val dateReservation: Date,
    val typePayment: String,
    val etat: Boolean,
    val idUser: String,
    val idVelo: String,
    val promoCode: String,
    val stripeCheckoutSessionId: String
)

data class ReservationResponse(
    val id: String,  // Add the 'id' field
    val dateReservation: Date,
    val typePayment: String,
    val etat: Boolean,
    val idUser: String,
    val idVelo: String,
    val promoCode: String,
    val stripeCheckoutSessionId: String,
    val message: String
)
