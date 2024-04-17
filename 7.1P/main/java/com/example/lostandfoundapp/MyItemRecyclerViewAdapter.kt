package com.example.lostandfoundapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

//import com.example.taskmanager.placeholder.PlaceholderContent.PlaceholderItem
import com.example.lostandfoundapp.databinding.FragmentItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val allItem: List<Item>,
    private val clickListener: Clickable
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindCard(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: FragmentItemBinding, private val clickListener:Clickable) : RecyclerView.ViewHolder(binding.root) {
        fun bindCard(item:Item){

            binding.editTitle.text = item.type + " " + item.name

            binding.noteView.setOnClickListener(){
                clickListener.onClick(item)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + binding.editTitle.text + "'"
        }
    }

}