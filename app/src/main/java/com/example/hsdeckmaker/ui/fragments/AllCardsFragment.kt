package com.example.hsdeckmaker.ui.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
import com.example.hsdeckmaker.ui.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_all_cards.view.*


class AllCardsFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private val cards = ArrayList<CardItem>()
    private val cardAdapter = CardAdapter(cards) {card -> onCardClick(card) }
    private lateinit var viewOfLayout: View
    private var cardModel: CardSingleViewModel?=null

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null
    
    companion object {
        fun newInstance(): Fragment {
            return AllCardsFragment()
        }
    }

    private lateinit var gridLayoutManager: GridLayoutManager;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater!!.inflate(R.layout.fragment_all_cards, container, false)
        val gridLayoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        viewOfLayout.rvCards.layoutManager = gridLayoutManager
        getActivity()?.setTitle(getString(R.string.home_text));

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
        setHasOptionsMenu(true);
        initViewModel()
        viewModel.getCards()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager =
            activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }

        if (searchView != null) {

            // Handle search
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(query: String): Boolean {
                    cardAdapter.getFilter().filter(query);
                    cardAdapter.notifyDataSetChanged()
                    return true
                }
                override fun onQueryTextSubmit(query: String): Boolean {
                    cardAdapter.getFilter().filter(query);
                    cardAdapter.notifyDataSetChanged()
                    return true
                }
            }
            searchView!!.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.cardsPage.observe(this, Observer {
            Log.d("er veranderd iets", it.toString())
            cards.clear()
            cards.addAll(it)
            cardAdapter.notifyDataSetChanged()
        })

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