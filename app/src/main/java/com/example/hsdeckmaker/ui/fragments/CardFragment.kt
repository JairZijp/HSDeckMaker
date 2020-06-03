package com.example.hsdeckmaker.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.database.CardRepository
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.ui.*
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
                        val card = CardItem(o.id, o.name, o.text)
                        viewModel.insertCard(card)
                        Toast.makeText(activity, "Card added to deck!", Toast.LENGTH_SHORT).show()
//                        val resultIntent = Intent()
//                        resultIntent.putExtra(EXTRA_CARD, card)
//                        activity?.setResult(Activity.RESULT_OK, resultIntent)
//                        activity?.finish()
                    }
                } else {
                    btn.text = "Remove from deck"
                }
            }
        })
    }

    // Check if card is already in deck
    fun isCardInDeck(id: String) : Boolean {
        val cardt : CardItem? = cardRepository?.findById(id)
        Log.d("isCardInDeck", cardt.toString())

        if (cardt == null) {
            return true
        }
        return false
    }

    override fun onBackPressed(): Boolean {
        getActivity()?.setTitle(getString(R.string.home_text));
        return true
    }
}