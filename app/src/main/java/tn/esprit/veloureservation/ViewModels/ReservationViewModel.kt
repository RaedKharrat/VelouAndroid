package tn.esprit.veloureservation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.veloureservation.Models.ReservationResponse
import tn.esprit.veloureservation.Models.ReservationRequest
import tn.esprit.veloureservation.Utils.RetrofitImp
import java.util.Date

class ReservationViewModel : ViewModel() {

    private val _ReservationResult = MutableLiveData<String>()
    val ReservationResult: LiveData<String> get() = _ReservationResult

    private val apiService = RetrofitImp.apiService

    fun commandeVelo(request: ReservationRequest) {
        val call = apiService.commandeVelo(request)

        call.enqueue(object : Callback<ReservationResponse> {
            override fun onResponse(call: Call<ReservationResponse>, response: Response<ReservationResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()?.message ?: "yesss message"
                    _ReservationResult.value = message
                } else {
                    // Handle unsuccessful response
                    _ReservationResult.value = "reservation failed"
                }
            }

            override fun onFailure(call: Call<ReservationResponse>, t: Throwable) {
                // Handle failure
                _ReservationResult.value = "reservation failed"
            }
        })
    }
}
