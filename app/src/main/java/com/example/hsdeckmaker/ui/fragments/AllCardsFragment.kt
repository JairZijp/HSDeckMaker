package com.example.hsdeckmaker.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.model.Card
import kotlinx.android.synthetic.main.fragment_all_cards.view.*
import com.example.hsdeckmaker.model.CardAdapter
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.ui.*
import kotlinx.android.synthetic.main.fragment_all_cards.view.*

class AllCardsFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private val cards = ArrayList<CardItem>()
    private val cardAdapter = CardAdapter(cards) {card -> onCardClick(card) }
    private lateinit var viewOfLayout: View
    private var cardModel: CardSingleViewModel?=null

    companion object {
        fun newInstance(): Fragment {
            return AllCardsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater!!.inflate(R.layout.fragment_all_cards, container, false)
        val gridLayoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        viewOfLayout.rvCards.layoutManager = gridLayoutManager

        viewOfLayout.rvCards.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewOfLayout.rvCards.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.requestLayout()
            }
        })

        viewOfLayout.rvCards.adapter = cardAdapter
        return viewOfLayout;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        viewModel.getCards()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.cardsPage.observe(this, Observer {
            cards.clear()
            cards.addAll(it)
            cardAdapter.notifyDataSetChanged()
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(getActivity(), it, Toast.LENGTH_LONG).show()
        })
    }

    private fun onCardClick(card: CardItem) {
        Log.d("onCardC;ocl", card.toString())
        cardModel = ViewModelProviders.of(activity!!).get(CardSingleViewModel::class.java)
        cardModel!!.setCard(card)

        val myfragment = CardFragment()
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, myfragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}