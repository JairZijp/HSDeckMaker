package com.example.hsdeckmaker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.model.Card
import com.example.hsdeckmaker.model.CardAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val cards = arrayListOf<Card>()
    private val cardAdapter = CardAdapter(cards)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        val gridLayoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        rvCards.layoutManager = gridLayoutManager

        // Add Global Layout Listener to calculate the span count.
        rvCards.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rvCards.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.requestLayout()
            }
        })


        rvCards.adapter = cardAdapter

        viewModel.getCards()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.cardsPage.observe(this, Observer {
            cards.clear()
            cards.addAll(it.cards)
            cardAdapter.notifyDataSetChanged()
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }
}