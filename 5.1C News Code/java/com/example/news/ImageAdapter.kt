package com.example.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ImageCellBinding

class ImageAdapter(val cards: List<Articles>, private val clickListener:Clickable): RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ImageCellBinding.inflate(from, parent, false)
        return ImageViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindCard(cards[position])
    }
}

class ImageViewHolder(private  val imageCellBinding:ImageCellBinding, private val clickListener:Clickable) : RecyclerView.ViewHolder(imageCellBinding.root) {
    fun bindCard(dest:Articles){
        imageCellBinding.mainImage.setImageResource(dest.img)

        imageCellBinding.cardView.setOnClickListener(){
            clickListener.onClick(dest)
        }
    }
}