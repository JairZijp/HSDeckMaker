package com.example.hsdeckmaker.api

import com.example.hsdeckmaker.model.CardPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

public interface CardsApiService {
    @GET("/25770/enUS/cards.json")
    fun getAllCards(): Call<CardPage>
}