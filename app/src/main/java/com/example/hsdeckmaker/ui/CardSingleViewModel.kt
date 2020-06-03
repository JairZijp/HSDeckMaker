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
}