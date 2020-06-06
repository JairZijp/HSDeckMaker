package com.example.hsdeckmaker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hsdeckmaker.database.CardRepository
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.model.Deck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeckViewModel(application: Application) : AndroidViewModel(application) {

    private val cardsRepository = CardRepository(application.applicationContext)
    private val ioScope = CoroutineScope(Dispatchers.IO)
    var name: String = ""

    fun insertDeck(deck: Deck) {
        ioScope.launch {
            cardsRepository.insertDeck(deck)
        }
    }

}