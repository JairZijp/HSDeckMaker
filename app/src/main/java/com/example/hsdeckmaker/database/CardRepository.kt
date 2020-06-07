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

    fun findById(id: String): CardItem {
        return cardDao.findById(id)
    }

    fun getCards(): LiveData<List<CardItem>> {
        return cardDao.getCards()
    }

    fun getCardsFromDeck(deck_id: Int): List<Deck> {
        return cardDao.getCardsFromDeck(deck_id)
    }

    fun getDecks(): List<Deck> {
        return cardDao.getDecks()
    }

    suspend fun insertCard(card: CardItem) {
        return cardDao.insertCard(card)
    }

    suspend fun insertDeck(deck: Deck) {
        return cardDao.insertDeck(deck)
    }

    fun isCardInDeck(card_id: String, deck_id: Int): Int {
        Log.d("cardid", card_id)
        Log.d("deckid", deck_id.toString())

        return cardDao.findCardInDeck(card_id, deck_id)
    }

}