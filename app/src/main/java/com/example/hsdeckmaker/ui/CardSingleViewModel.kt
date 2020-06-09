package com.example.hsdeckmaker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hsdeckmaker.database.CardRepository
import com.example.hsdeckmaker.model.CardItem

class CardSingleViewModel(application: Application) : AndroidViewModel(application) {

    private val cardRepository = CardRepository(application.applicationContext)

    val cardItem = MutableLiveData<CardItem>()

    fun setCard(card:CardItem) = cardItem.setValue(card)

    // Check if card is already in deck
    fun isCardInDeck(card_id: String, deck_id: Int?) : Boolean {
        val check = cardRepository.isCardInDeck(card_id, deck_id!!)

        if (check == 1) {
            return true
        }
        return false
    }

}