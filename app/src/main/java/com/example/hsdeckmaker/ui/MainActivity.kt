package com.example.hsdeckmaker.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.model.CardAdapter
import com.example.hsdeckmaker.model.CardItem
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_CARD = "EXTRA_CARD"

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val cards = ArrayList<CardItem>()
    private val cardAdapter = CardAdapter(cards) {card -> onCardClick(card) }

    private fun isViewModelInitialized() = ::viewModel.isInitialized

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setTitle("All Cards");

        initViews()
        initViewModel()
        if(isViewModelInitialized()) {
            viewModel.getCards()
        }
    }

    private fun initViews() {
        val gridLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        rvCards.layoutManager = gridLayoutManager

        rvCards.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rvCards.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.requestLayout()
            }
        })

        rvCards.adapter = cardAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.cardsPage.observe(this, Observer {
            cards.clear()
            cards.addAll(it)
            cardAdapter.notifyDataSetChanged()
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun onCardClick(card: CardItem) {
        val intent = Intent(this, CardSingleActivity::class.java)
        Log.d("intent_log", card.toString())
        val bundle = Bundle()
        bundle.putParcelable("selected_card", card)
        intent.putExtra(EXTRA_CARD, bundle)

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    val card = data!!.getParcelableExtra<CardItem>(EXTRA_CARD)
                    viewModel.insertCard(card)
                    Toast.makeText(applicationContext, "Card added to deck!", Toast.LENGTH_SHORT).show()
                }
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}