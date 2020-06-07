package com.example.hsdeckmaker.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.hsdeckmaker.R
import com.example.hsdeckmaker.model.CardItem
import com.example.hsdeckmaker.ui.fragments.AllCardsFragment
import com.example.hsdeckmaker.ui.fragments.DeckFragment
import com.example.hsdeckmaker.ui.fragments.NewDeckFragment
import kotlinx.android.synthetic.main.activity_main.*


const val EXTRA_CARD = "EXTRA_CARD"

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val cards = ArrayList<CardItem>()
    private lateinit var mDrawerLayout: DrawerLayout

    private fun isViewModelInitialized() = ::viewModel.isInitialized
    var navigationPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setTitle("All Cards");

        initViews()
    }

    private fun initViews() {

        val deckModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        val menu = navigationView.menu
        setSupportActionBar(toolbar)
        setUpDrawerLayout()

        //Load All cards fragment first
        navigationPosition = R.id.all_cards
        navigateToFragment(AllCardsFragment.newInstance())

        navigationView.setCheckedItem(navigationPosition)
        toolbar.title = getString(R.string.home_text)

        // Add decks to menu
        for (i in 0 until deckModel.getDecks().size) {
            menu.add(R.id.grp2, deckModel.getDecks()[i].id!!, 0, deckModel.getDecks()[i].name);
        }

        // Navigation items
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.all_cards -> {
                    toolbar.title = getString(R.string.home_text)
                    navigationPosition = R.id.all_cards
                    navigateToFragment(AllCardsFragment.newInstance())
                }
                R.id.my_deck -> {
                    toolbar.title = getString(R.string.my_deck)
                    navigationPosition = R.id.my_deck
                    navigateToFragment(DeckFragment.newInstance())
                }

                R.id.new_deck -> {
                    toolbar.title = getString(R.string.my_deck)
                    navigationPosition = R.id.new_deck
                    navigateToFragment(NewDeckFragment.newInstance())
                }
            }
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun setUpDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.frameLayout)
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    val card = data!!.getParcelableExtra<CardItem>(EXTRA_CARD)
                    viewModel.insertCard(card)
                    Toast.makeText(applicationContext, "Card added to deck!", Toast.LENGTH_SHORT).show()
                }
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}