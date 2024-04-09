package com.example.taskmanager

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

//import com.example.taskmanager.placeholder.PlaceholderContent.PlaceholderItem
import com.example.taskmanager.databinding.FragmentItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyTaskRecyclerViewAdapter(
    private val allTasks: List<Task>,
    private val clickListener: Clickable
) : RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder>() {

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
        val task = tasks[position]
        holder.bindCard(task)
    }

    override fun getItemCount(): Int = tasks.size

    inner class ViewHolder(private val binding: FragmentItemBinding, private val clickListener:Clickable) : RecyclerView.ViewHolder(binding.root) {
        fun bindCard(task:Task){

            binding.editTitle.text = task.title

            binding.noteView.setOnClickListener(){
                clickListener.onClick(task)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + binding.editTitle.text + "'"
        }
    }

}