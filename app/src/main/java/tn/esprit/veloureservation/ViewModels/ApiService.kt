// ApiService.kt
package tn.esprit.veloureservation.ViewModels

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import tn.esprit.veloureservation.Models.ReservationRequest
import tn.esprit.veloureservation.Models.ReservationResponse

interface ApiService {

    @POST("reservation/reservation/655e87de5c69918a939e20f9/655d1d936d213ea3741af704")
    fun commandeVelo(@Body request: ReservationRequest): Call<ReservationResponse>

    @GET("reservation/reservations/655e87de5c69918a939e20f9")
    fun getReservationsForUser(): Call<List<ReservationResponse>>

    @DELETE("reservation/reservation/{id}")
    fun deleteReservation(@Path("id") id: String): Call<Void>

    // Add other Retrofit service methods if needed
}
