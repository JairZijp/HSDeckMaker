package com.example.hsdeckmaker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.model.CardAdapter
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.replaceFragment
import com.example.hsdeckmaker.ui.CardSingleViewModel
import com.example.hsdeckmaker.ui.DeckViewModel
import com.example.hsdeckmaker.ui.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_deck.view.*

class DeckFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewOfLayout: View
    private lateinit var cardModel: CardSingleViewModel
    private lateinit var deckModel: DeckViewModel
    private val cards = ArrayList<CardItem>()
    private val cardAdapter = CardAdapter(cards) {card -> onCardClick(card) }
    private var deckName by FragmentArgumentDelegate<String>()
    private var deckId by FragmentArgumentDelegate<Int>()

    companion object {
        fun newInstance(deckName: String, deckId: Int) = DeckFragment().apply {
            this.deckName = deckName
            this.deckId = deckId
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        deckModel.getCardsFromDeck(deckId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater!!.inflate(R.layout.fragment_deck, container, false)
        val gridLayoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        viewOfLayout.deckCards.layoutManager = gridLayoutManager

        viewOfLayout.deckCards.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewOfLayout.deckCards.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.requestLayout()
            }
        })

        viewOfLayout.deckCards.adapter = cardAdapter
        return viewOfLayout;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cards.addAll(deckModel.cardsPage)
        getActivity()?.setTitle(deckName);
    }

    private fun initViewModel() {
        deckModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.error.observe(this, Observer {
            Toast.makeText(getActivity(), it, Toast.LENGTH_LONG).show()
        })
    }

    private fun onCardClick(card: CardItem) {
        cardModel = ViewModelProviders.of(activity!!).get(CardSingleViewModel::class.java)
        cardModel!!.setCard(card)

        fragmentManager?.replaceFragment(CardFragment())
    }

}