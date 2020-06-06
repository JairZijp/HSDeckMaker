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
@Entity(tableName = "cards", primaryKeys = ["id", "deck_id"])
data class CardItem(

    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,

    @SerializedName("text")
    @ColumnInfo(name = "text")
    val text: String,

    @SerializedName("deck_id")
    @ColumnInfo(name = "deck_id")
    val deck_id: Int

) : Parcelable {
    fun getCardImage() = "https://art.hearthstonejson.com/v1/render/latest/enUS/512x/$id.png"
}