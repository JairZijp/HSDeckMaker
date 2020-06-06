package com.example.hsdeckmaker.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "decks")
data class Deck(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int? = 0,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String

) : Parcelable