package com.example.hsdeckmaker.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.database.CardRepository
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.replaceFragment
import com.example.hsdeckmaker.ui.CardSingleViewModel
import com.example.hsdeckmaker.ui.DeckViewModel
import com.example.hsdeckmaker.ui.IOnBackPressed
import com.example.hsdeckmaker.ui.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_single_cart.view.*


class CardFragment : Fragment(), IOnBackPressed {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var card: CardItem
    private lateinit var viewOfLayout: View
    private val cardRepository = activity?.applicationContext?.let { CardRepository(it) }

    companion object {
        fun newInstance(): Fragment {
            return CardFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater!!.inflate(R.layout.fragment_single_cart, container, false)
        val linearLayout = viewOfLayout.findViewById(R.id.linearLayout) as LinearLayout
        val deckModel = ViewModelProvider(this).get(DeckViewModel::class.java)

        for (i in 0 until deckModel.getDecks().size) {
            val cb = CheckBox(activity)
            cb.text = deckModel.getDecks()[i].name
            cb.setPadding(10, 10, 10, 10);
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(5, 5, 5, 5)
            cb.setLayoutParams(params);
            cb.id = deckModel.getDecks()[i].id!!
            linearLayout.addView(cb)
        }

        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = viewOfLayout.button;
        val cardModel = ViewModelProviders.of(activity!!).get(CardSingleViewModel::class.java)
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val deckModel = ViewModelProvider(this).get(DeckViewModel::class.java)

        cardModel.cardItem.observe(viewLifecycleOwner, object : Observer<CardItem> {
            override fun onChanged(o: CardItem?) {
                getActivity()?.setTitle(o!!.name);
                view.description.text = o!!.text
                getActivity()?.let { Glide.with(it).load(o.getCardImage()).into(view.ivPoster) }

                // Check the checkbox if the card is in that deck
                for (i in 0 until deckModel.getDecks().size) {
                    if(cardModel.isCardInDeck(o.id, deckModel.getDecks()[i].id)) {
                        viewOfLayout.findViewById<CheckBox>(deckModel.getDecks()[i].id!!).isChecked = true
                    }
                }

                btn.text = getString(R.string.save)
                btn.setOnClickListener {
                    Log.d("db add fragment", o.name)

                    for (i in 0 until deckModel.getDecks().size) {

                        // Check which deck checkboxes are checked, save the cards in the checked decks
                        if(viewOfLayout.findViewById<CheckBox>(deckModel.getDecks()[i].id!!).isChecked) {
                            val card = CardItem(o.id, o.name, o.text, deckModel.getDecks()[i].id!!)
                            viewModel.insertCard(card)
                        } else {
                            val card = CardItem(o.id, o.name, o.text, deckModel.getDecks()[i].id!!)
                            viewModel.removeCard(card)
                        }
                    }

                    Toast.makeText(activity, getString(R.string.save_text), Toast.LENGTH_SHORT).show()
                    
                    fragmentManager?.replaceFragment(AllCardsFragment())
                }
            }
        })
    }

    override fun onBackPressed(): Boolean {
        getActivity()?.setTitle(getString(R.string.home_text));
        return true
    }
}