package com.example.itube

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.itube.databinding.FragmentItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPlaylistRecyclerViewAdapter(
//    private val allTasks: List<Task>,
    private val currentPlaylist: List<String>
) : RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder>() {

//    private val currentPlaylist: List<String> = users[index].playlist
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = currentPlaylist[position]
        holder.bindCard(video)
    }

    override fun getItemCount(): Int = currentPlaylist.size

    inner class ViewHolder(private val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindCard(video:String){

            binding.editTitle.text = video
        }

        override fun toString(): String {
            return super.toString() + " '" + binding.editTitle.text + "'"
        }
    }

}