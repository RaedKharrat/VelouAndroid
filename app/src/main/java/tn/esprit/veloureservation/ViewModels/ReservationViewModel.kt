package tn.esprit.veloureservation.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.veloureservation.Models.ReservationResponse
import tn.esprit.veloureservation.Models.ReservationRequest
import tn.esprit.veloureservation.Utils.RetrofitImp

class ReservationViewModel : ViewModel() {

    private val _reservationResult = MutableLiveData<List<ReservationResponse>?>()
    val reservationResult: LiveData<List<ReservationResponse>?> get() = _reservationResult

    private val apiService = RetrofitImp.apiService

    fun commandeVelo(request: ReservationRequest) {
        val call = apiService.commandeVelo(request)

        call.enqueue(object : Callback<ReservationResponse> {
            override fun onResponse(call: Call<ReservationResponse>, response: Response<ReservationResponse>) {
                if (response.isSuccessful) {
                    // Handle successful response if needed
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<ReservationResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    fun getReservationsForUser(userId: String) {
        val call = apiService.getReservationsForUser()

        call.enqueue(object : Callback<List<ReservationResponse>> {
            override fun onResponse(call: Call<List<ReservationResponse>>, response: Response<List<ReservationResponse>>) {
                if (response.isSuccessful) {
                    val reservations = response.body()
                    _reservationResult.value = reservations
                } else {
                    // Handle unsuccessful response
                    _reservationResult.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<ReservationResponse>>, t: Throwable) {
                // Handle failure
                _reservationResult.value = emptyList()
            }
        })
    }

   /* fun getReservationsForUser(userId: String) {
        val call = apiService.getReservationsForUser(userId)

        call.enqueue(object : Callback<List<ReservationResponse>> {
            override fun onResponse(call: Call<List<ReservationResponse>>, response: Response<List<ReservationResponse>>) {
                if (response.isSuccessful) {
                    val reservations = response.body()
                    _reservationResult.value = reservations
                } else {
                    // Handle unsuccessful response
                    _reservationResult.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<ReservationResponse>>, t: Throwable) {
                // Handle failure
                _reservationResult.value = emptyList()
            }
        })
    }*/



    fun deleteReservation(id: String) {
        Log.d("ReservationViewModel", "Deleting reservation with ID: $id")
        val call = apiService.deleteReservation(id)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Handle successful response if needed
                    Log.d("ReservationViewModel", "Reservation deleted successfully")
                } else {
                    // Handle unsuccessful response
                    Log.e("ReservationViewModel", "Failed to delete reservation on response. Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle failure
                Log.e("ReservationViewModel", "Failed to delete reservation on fail", t)
            }
        })
    }

}
