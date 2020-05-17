package com.example.hsdeckmaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("text") var text: String
) : Parcelable