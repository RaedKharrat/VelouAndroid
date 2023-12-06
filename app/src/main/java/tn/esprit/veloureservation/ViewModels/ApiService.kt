package tn.esprit.veloureservation.ViewModels

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import tn.esprit.veloureservation.Models.ReservationRequest
import tn.esprit.veloureservation.Models.ReservationResponse

interface ApiService {

     @POST("reservation/reservation/655e87de5c69918a939e20f9/655d1d936d213ea3741af704") // Adjust the endpoint path accordingly
    fun commandeVelo(@Body request: ReservationRequest): Call<ReservationResponse>

    // Add other Retrofit service methods if needed
}
