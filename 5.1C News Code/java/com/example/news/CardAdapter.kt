package com.example.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.CardCellBinding

class CardAdapter(val cards: List<Articles>, private val clickListener:Clickable): RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent, false)
        return CardViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindCard(cards[position])
    }
}

class CardViewHolder(private  val cardCellBinding:CardCellBinding, private val clickListener:Clickable) : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindCard(dest:Articles){
        cardCellBinding.titleText.text = dest.title
        cardCellBinding.descText.text = dest.desc
        cardCellBinding.mainImage.setImageResource(dest.img)

        cardCellBinding.cardView.setOnClickListener(){
            clickListener.onClick(dest)
        }
    }
}