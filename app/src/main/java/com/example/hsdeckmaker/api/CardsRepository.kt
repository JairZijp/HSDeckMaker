package com.example.hsdeckmaker.api

import android.content.Context

class CardsRepository(var context: Context) {
    private val cardsApi: CardsApiService = CardsApi.createApi()

    fun getCards() = cardsApi.getAllCards()
}