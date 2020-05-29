package com.example.hsdeckmaker.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.hsdeckmaker.model.CardItem

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

    suspend fun insertCard(card: CardItem) {
        return cardDao.insertCard(card)
    }

}