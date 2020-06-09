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

    @Query("SELECT * FROM cards WHERE id = :id")
    fun findById(id: String): CardItem

    @Insert
    suspend fun insertCard(card: CardItem)

    @Delete
    suspend fun removeCard(card: CardItem)

    @Insert
    suspend fun insertDeck(deck: Deck)

    @Query("SELECT * FROM cards WHERE deck_id = :deckId")
    fun getCardsFromDeck(deckId: Int): List<CardItem>

    @Query("SELECT * FROM cards WHERE name LIKE :query+'%'")
    fun getCardsSearch(query: String): List<CardItem>

    @Query("SELECT * FROM decks")
    fun getDecks(): List<Deck>

    @Query("SELECT COUNT(*) FROM cards WHERE id = :cardId AND deck_id = :deckId")
    fun findCardInDeck(cardId: String, deckId: Int): Int
}