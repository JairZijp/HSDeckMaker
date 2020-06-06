package com.example.hsdeckmaker.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert
import androidx.lifecycle.LiveData
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.model.Deck

@Dao
interface CardDao {
    @Query("SELECT * FROM cards")
    fun getCards(): LiveData<List<CardItem>>

    @Query("SELECT * FROM cards where id = :id")
    fun findById(id: String): CardItem

    @Insert
    suspend fun insertCard(card: CardItem)

    @Insert
    suspend fun insertDeck(deck: Deck)

    @Query("SELECT * FROM cards where deck_id = :deckId")
    fun getCardsFromDeck(deckId: String): LiveData<List<CardItem>>
}