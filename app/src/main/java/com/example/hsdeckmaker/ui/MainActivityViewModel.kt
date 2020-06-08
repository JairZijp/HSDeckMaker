package com.example.hsdeckmaker.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hsdeckmaker.api.CardsRepository
import com.example.hsdeckmaker.database.CardRepository
import com.example.hsdeckmaker.model.Card
import com.example.hsdeckmaker.model.CardItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val cardsRepository = CardsRepository(application.applicationContext)
    val cardsPage = MutableLiveData<Card>()
    val error = MutableLiveData<String>()

    private val cardRepository = CardRepository(application.applicationContext)
    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun getCards() {
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
            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                error.value = t.message
            }
        })

    }

    fun insertCard(card: CardItem) {
        ioScope.launch {
            cardRepository.insertCard(card)
        }
    }

    fun removeCard(card: CardItem) {
        ioScope.launch {
            cardRepository.removeCard(card)
        }
    }

}