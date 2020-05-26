package com.example.hsdeckmaker.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.model.CardItem
import kotlinx.android.synthetic.main.card_single.*


class CardSingleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_single)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
    }

    private fun initViews() {
        val bundle = intent?.getBundleExtra("EXTRA_CARD")
        val card  = bundle?.getParcelable<CardItem>("selected_card") as CardItem
        this.setTitle(card.name);
        description.text = card.text
        Glide.with(this).load(card.getCardImage()).into(ivPoster)
    }
}
