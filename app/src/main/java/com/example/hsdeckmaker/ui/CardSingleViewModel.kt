package com.example.hsdeckmaker.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.hsdeckmaker.database.CardRepository
import com.example.hsdeckmaker.model.CardItem

class CardSingleViewModel(application: Application) : AndroidViewModel(application) {

    private val cardRepository = CardRepository(application.applicationContext)

    fun isCardInDeck(id: String) : Boolean {
        val card : CardItem? = cardRepository.findById(id)

        if (card == null) {
            return true
        }

        return false
    }
}