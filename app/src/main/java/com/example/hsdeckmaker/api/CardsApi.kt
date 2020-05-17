package com.example.hsdeckmaker.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CardsApi {
    companion object {
        private const val baseUrl = "https://api.hearthstonejson.com/v1"

        /**
         * @return [CardsApiService] The service class off the retrofit client.
         */

        fun createApi(): CardsApiService {
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val cardsApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return cardsApi.create(CardsApiService::class.java)
        }
    }
}