package com.example.hsdeckmaker.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class Card : ArrayList<CardItem>()

@Parcelize
@Entity(tableName = "cards")
data class CardItem(

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: String,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,

    @SerializedName("text")
    @ColumnInfo(name = "text")
    val text: String

) : Parcelable {
    fun getCardImage() = "https://art.hearthstonejson.com/v1/render/latest/enUS/512x/$id.png"
}