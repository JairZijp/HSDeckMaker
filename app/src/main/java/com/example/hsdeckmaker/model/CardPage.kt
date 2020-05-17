package com.example.hsdeckmaker.model
import com.google.gson.annotations.SerializedName

data class CardPage(
    @SerializedName("results") var cards: List<Card>
)