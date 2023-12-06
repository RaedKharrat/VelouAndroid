package tn.esprit.veloureservation.Models

import java.util.Date

data class ReservationRequest (
    val dateReservation: Date,
    val typePayment: String,
    val etat: Boolean,
    val idUser: String,
    val idVelo: String,
    val promoCode: String,
    val stripeCheckoutSessionId: String
)




data class  ReservationResponse (
     val message: String
         )

/*
data class FarmerSignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val numTel: String
)

data class FarmerSignUpResponse(
    val message: String
)*/