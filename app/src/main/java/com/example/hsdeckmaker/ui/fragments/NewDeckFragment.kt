package com.example.hsdeckmaker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.model.Deck
import com.example.hsdeckmaker.replaceFragment
import com.example.hsdeckmaker.ui.DeckViewModel
import kotlinx.android.synthetic.main.fragment_new_deck.view.*

class NewDeckFragment : Fragment() {

    private lateinit var viewModel: DeckViewModel
    private lateinit var viewOfLayout: View

    companion object {
        fun newInstance(): Fragment {
            return NewDeckFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity as AppCompatActivity).get(DeckViewModel::class.java)
        viewOfLayout = inflater!!.inflate(R.layout.fragment_new_deck, container, false)
        getActivity()?.setTitle(getString(R.string.new_deck));
        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewOfLayout.btnCreate.setOnClickListener {

            val deck = Deck(null, viewOfLayout.textDeck.text.toString())
            viewModel.insertDeck(deck)
            Toast.makeText(activity, "New deck: " + viewOfLayout.textDeck.text.toString() + " created", Toast.LENGTH_SHORT).show()

            fragmentManager?.replaceFragment(AllCardsFragment())
        }

    }

}