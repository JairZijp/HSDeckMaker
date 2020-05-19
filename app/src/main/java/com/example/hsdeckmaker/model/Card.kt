package com.example.hsdeckmaker.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class Card : ArrayList<CardItem>()

@SuppressLint("ParcelCreator")
@Parcelize
data class CardItem(
    val armor: Int,
    val artist: String,
    val attack: Int,
    val cardClass: String,
    val classes: List<String>,
    val collectible: Boolean,
    val collectionText: String,
    val cost: Int,
    val dbfId: Int,
    val durability: Int,
    val elite: Boolean,
    val entourage: List<String>,
    val faction: String,
    val flavor: String,
    val health: Int,
    val hideStats: Boolean,
    val howToEarn: String,
    val howToEarnGolden: String,
    val id: String,
    val mechanics: List<String>,
    val multiClassGroup: String,
    val name: String,
    val overload: Int,
    val playRequirements: PlayRequirements,
    val questReward: String,
    val race: String,
    val rarity: String,
    val referencedTags: List<String>,
    val `set`: String,
    val spellDamage: Int,
    val targetingArrowText: String,
    val text: String,
    val type: String
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class PlayRequirements(
    val REQ_FRIENDLY_TARGET: Int,
    val REQ_MINION_TARGET: Int,
    val REQ_TARGET_TO_PLAY: Int
) : Parcelable