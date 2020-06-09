package com.example.hsdeckmaker.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hsdeckmaker.R
import kotlinx.android.synthetic.main.card_item.view.*


class CardAdapter(private var cards: List<CardItem>,
                  private val onClick: (CardItem) -> Unit
) : RecyclerView.Adapter<CardAdapter.ViewHolder>(),
    Filterable {

    private lateinit var context: Context
    var initialCards = cards

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position], position+1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick(cards[adapterPosition])
            }
        }

        fun bind(card: CardItem, i: Int) {
            Glide.with(context).load(card.getCardImage()).into(itemView.ivCard)
            itemView.tvCard.text = card.name
        }
    }

    // Setup custom filter
    override fun getFilter() : Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                cards = filterResults.values as List<CardItem>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()
                val filterResults = Filter.FilterResults()

                filterResults.values = if (queryString==null || queryString.isEmpty())
                    initialCards
                else
                    // Filter on the name of the cards
                    cards.filter {
                        it.name.toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }

}