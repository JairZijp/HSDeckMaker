package com.example.hsdeckmaker.model

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hsdeckmaker.R.layout.card_item
import kotlinx.android.synthetic.main.card_item.view.*
import java.net.URL


public class CardAdapter(private val cards: List<Card>) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>()
    {

        lateinit var context: Context

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(card_item, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return cards.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(cards[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(card: Card) {
                Glide.with(context).load("https://art.hearthstonejson.com/v1/render/latest/enUS/256x/" + card.id).into(itemView.ivCard)
                itemView.tvCard.text = card.name
            }
        }
    }