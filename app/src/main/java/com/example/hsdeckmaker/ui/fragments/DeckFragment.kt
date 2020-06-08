package com.example.hsdeckmaker.ui.fragments

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
import com.example.hsdeckmaker.model.CardAdapter
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.ui.CardSingleViewModel
import com.example.hsdeckmaker.ui.DeckViewModel
import com.example.hsdeckmaker.ui.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_all_cards.view.*
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
        Log.d("CaRDSS", deckModel.cardsPage.toString())
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

        // TODO: create global method for this
        // findNavController is not working for some reason, seems to be a problem with gradle version:
        // https://stackoverflow.com/questions/51890039/android-unresolved-reference-findnavcontroller-error
        // Alternative:
        val myfragment = CardFragment()
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, myfragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}