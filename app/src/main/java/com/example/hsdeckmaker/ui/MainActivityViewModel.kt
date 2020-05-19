package com.example.hsdeckmaker.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hsdeckmaker.api.CardsRepository
import com.example.hsdeckmaker.model.Card
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val cardsRepository = CardsRepository(application.applicationContext)
    val cardsPage = MutableLiveData<Card>()
    val error = MutableLiveData<String>()
    val progressBarStatus = MutableLiveData<Boolean>(false)

    fun getCards() {

        progressBarStatus.value = true
        val call: Call<Card> = cardsRepository.getCards()
        call.enqueue(object : Callback<Card> {
            override fun onResponse(
                call: Call<Card>,
                response: Response<Card>
            ) {

                if (response.isSuccessful) {
                    cardsPage.value = response.body()
                }
                else error.value = "An error occurred: ${response.errorBody().toString()}"
                Log.d("M8?", cardsPage.value.toString())
                progressBarStatus.value = false
            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                error.value = t.message
                progressBarStatus.value = false
            }
        })

    }



}