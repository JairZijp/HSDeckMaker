package com.example.hsdeckmaker.database

import android.content.Context
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

    fun getCardsFromDeck(deck_id: String): LiveData<List<CardItem>> {
        return cardDao.getCardsFromDeck(deck_id)
    }

    suspend fun insertCard(card: CardItem) {
        return cardDao.insertCard(card)
    }

    suspend fun insertDeck(deck: Deck) {
        return cardDao.insertDeck(deck)
    }


}