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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hsdeckmaker.R
import kotlinx.android.synthetic.main.fragment_all_cards.view.*
import com.example.hsdeckmaker.model.CardAdapter
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.ui.CardSingleActivity
import com.example.hsdeckmaker.ui.EXTRA_CARD
import com.example.hsdeckmaker.ui.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_all_cards.view.*

class AllCardsFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private val cards = ArrayList<CardItem>()
    private val cardAdapter = CardAdapter(cards) {card -> onCardClick(card) }
    private lateinit var viewOfLayout: View

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
        val intent = Intent(getActivity(), CardSingleActivity::class.java)
        Log.d("intent_log", card.toString())
        val bundle = Bundle()
        bundle.putParcelable("selected_card", card)
        intent.putExtra(EXTRA_CARD, bundle)

        startActivityForResult(intent, 100)
    }
}