package com.example.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.RelatedCellBinding


class RelatedAdapter(val cards: List<Articles>, private val clickListener:Clickable): RecyclerView.Adapter<RelatedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = RelatedCellBinding.inflate(from, parent, false)
        return RelatedViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: RelatedViewHolder, position: Int) {
        holder.bindCard(cards[position])
    }
}

class RelatedViewHolder(private  val relatedCellBinding:RelatedCellBinding, private val clickListener:Clickable) : RecyclerView.ViewHolder(relatedCellBinding.root) {
    fun bindCard(dest:Articles){
        relatedCellBinding.titleText.text = dest.title
        relatedCellBinding.descText.text = dest.desc
        relatedCellBinding.mainImage.setImageResource(dest.img)
        relatedCellBinding.cardView.setOnClickListener(){
            clickListener.onClick(dest)
        }
    }
}