package com.example.hsdeckmaker.api

import com.example.hsdeckmaker.model.Card
import com.example.hsdeckmaker.model.CardPage
import retrofit2.Call
import retrofit2.http.GET

public interface CardsApiService {
    @GET("/v1/25770/enUS/cards.collectible.json")
    fun getAllCards(): Call<CardPage>
}