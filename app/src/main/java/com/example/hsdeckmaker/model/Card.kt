package com.example.hsdeckmaker.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class Card : ArrayList<CardItem>()

@Parcelize
data class CardItem(
    val cardClass: String,
    val cost: Int,
    val dbfId: Int,
    val id: String,
    val name: String,
    val text: String,
    val type: String
) : Parcelable {
    fun getCardImage() = "https://art.hearthstonejson.com/v1/render/latest/enUS/512x/$id.png"
}