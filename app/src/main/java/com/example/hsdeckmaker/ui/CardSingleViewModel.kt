package com.example.hsdeckmaker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hsdeckmaker.database.CardRepository
import com.example.hsdeckmaker.model.Card
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.model.Deck
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardSingleViewModel(application: Application) : AndroidViewModel(application) {

    private val cardRepository = CardRepository(application.applicationContext)

    val cardItem = MutableLiveData<CardItem>()

    fun setCard(card:CardItem) = cardItem.setValue(card)

    fun getCardsFromDeck(deck_id: Int): List<Deck> {
        return cardRepository.getCardsFromDeck(deck_id);
    }

}