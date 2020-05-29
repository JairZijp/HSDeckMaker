package com.example.hsdeckmaker.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.model.CardItem
import kotlinx.android.synthetic.main.card_single.*

class CardSingleActivity : AppCompatActivity() {

    private lateinit var card: CardItem
    private lateinit var viewModel: CardSingleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_single)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
        initModel()

        val btn = findViewById<Button>(R.id.button)

        if (viewModel.isCardInDeck(card.id)) {
            btn.text = "Add to deck"
            btn.setOnClickListener {
                Log.d("db add", card.name)
                val card = CardItem(card.id, card.name, card.text)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_CARD, card)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        } else {
            btn.text = "Remove from deck"
        }
    }

    private fun initModel() {
        viewModel = ViewModelProvider(this).get(CardSingleViewModel::class.java)
    }

    private fun initViews() {
        val bundle = intent?.getBundleExtra("EXTRA_CARD")
        card  = bundle?.getParcelable<CardItem>("selected_card") as CardItem
        this.setTitle(card.name);
        description.text = card.text
        Glide.with(this).load(card.getCardImage()).into(ivPoster)
    }
}
