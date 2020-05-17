package com.example.hsdeckmaker.api

import android.content.Context
import com.example.hsdeckmaker.R

class CardsRepository(var context: Context) {
    private val cardsApi: CardsApiService = CardsApi.createApi()

    fun getCards() = cardsApi.getAllCards()
}