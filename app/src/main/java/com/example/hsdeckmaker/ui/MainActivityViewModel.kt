package com.example.hsdeckmaker.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hsdeckmaker.api.CardsRepository
import com.example.hsdeckmaker.model.CardPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val cardsRepository = CardsRepository(application.applicationContext)
    val cardsPage = MutableLiveData<CardPage>()
    val error = MutableLiveData<String>()
    val progressBarStatus = MutableLiveData<Boolean>(false)

    fun getCards() {

        progressBarStatus.value = true
        val call: Call<CardPage> = cardsRepository.getCards()
        call.enqueue(object : Callback<CardPage> {
            override fun onResponse(
                call: Call<CardPage>,
                response: Response<CardPage>
            ) {
                if (response.isSuccessful) cardsPage.value = response.body()
                else error.value = "An error occurred: ${response.errorBody().toString()}"
                progressBarStatus.value = false
            }

            override fun onFailure(call: Call<CardPage>, t: Throwable) {
                error.value = t.message
                progressBarStatus.value = false
            }
        })

    }



}