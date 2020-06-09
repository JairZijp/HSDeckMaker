package com.example.hsdeckmaker.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.model.Deck

class CardRepository(context: Context) {
    private val cardDao: CardDao

    init {
        val database = CardDatabase.getDatabase(context)
        cardDao = database!!.cardDao()
    }

    fun getCardsFromDeck(deck_id: Int): List<CardItem> {
        return cardDao.getCardsFromDeck(deck_id)
    }

    fun getCardsSearch(query: String): List<CardItem> {
        return cardDao.getCardsSearch(query)
    }

    fun getDecks(): List<Deck> {
        return cardDao.getDecks()
    }

    suspend fun insertCard(card: CardItem) {
        if(isCardInDeck(card.id, card.deck_id) == 0) {
            return cardDao.insertCard(card)
        }
    }

    suspend fun removeCard(card: CardItem) {
        if(isCardInDeck(card.id, card.deck_id) == 1) {
            return cardDao.removeCard(card)
        }
    }

    suspend fun insertDeck(deck: Deck) {
        return cardDao.insertDeck(deck)
    }

    fun isCardInDeck(card_id: String, deck_id: Int): Int {
        return cardDao.findCardInDeck(card_id, deck_id)
    }

}