package com.example.hsdeckmaker.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.database.CardRepository
import com.example.hsdeckmaker.model.CardItem
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

        //Log.d("Deck log: ", .toString())

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
            linearLayout.addView(cb)
        }

        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = viewOfLayout.button;
        val cardModel = ViewModelProviders.of(activity!!).get(CardSingleViewModel::class.java)
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        cardModel.cardItem.observe(viewLifecycleOwner, object : Observer<CardItem> {
            override fun onChanged(o: CardItem?) {
                getActivity()?.setTitle(o!!.name);
                view.description.text = o!!.text
                getActivity()?.let { Glide.with(it).load(o.getCardImage()).into(view.ivPoster) }

                if (isCardInDeck(o.id)) {
                    btn.text = "Add to deck"
                    btn.setOnClickListener {
                        Log.d("db add fragment", o.name)
                        val card = CardItem(o.id, o.name, o.text, 1)
                        viewModel.insertCard(card)
                        Toast.makeText(activity, "Card added to deck!", Toast.LENGTH_SHORT).show()

                        // TODO: create global method for this
                        // findNavController is not working for some reason, seems to be a problem with gradle version:
                        // https://stackoverflow.com/questions/51890039/android-unresolved-reference-findnavcontroller-error
                        // Alternative:
                        val myfragment = AllCardsFragment()
                        val fragmentTransaction = fragmentManager!!.beginTransaction()
                        fragmentTransaction.replace(R.id.frameLayout, myfragment)
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                } else {
                    btn.text = "Remove from deck"
                }
            }
        })
    }

    // Check if card is already in deck
    fun isCardInDeck(id: String) : Boolean {
        val card_ : CardItem? = cardRepository?.findById(id)
        Log.d("isCardInDeck", card_.toString())

        if (card_ == null) {
            return true
        }
        return false
    }

    override fun onBackPressed(): Boolean {
        getActivity()?.setTitle(getString(R.string.home_text));
        return true
    }
}