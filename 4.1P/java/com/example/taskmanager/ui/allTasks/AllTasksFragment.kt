package com.example.taskmanager.ui.allTasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.Clickable
import com.example.taskmanager.MyTaskRecyclerViewAdapter
import com.example.taskmanager.R
import com.example.taskmanager.Task
import com.example.taskmanager.database
import com.example.taskmanager.databinding.AllTasksBinding
import com.example.taskmanager.tasks
import kotlinx.coroutines.launch

class AllTasksFragment : Fragment(), Clickable {

    private var _binding: AllTasksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(AllTasksViewModel::class.java)

        _binding = AllTasksBinding.inflate(inflater, container, false)
//        val root: View = binding.root

/*        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        // Set the adapter

        recyclerView = _binding!!.items
        recyclerView.adapter = MyTaskRecyclerViewAdapter(tasks,this)
        recyclerView.layoutManager = GridLayoutManager(context, 1)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(task: Task) {
        super.onClick(task)
        lifecycleScope.launch {
            database.taskDao().getTasks().observe(viewLifecycleOwner, Observer {
                // Sort the list by due date before assigning it to tasks
                tasks = it.sortedBy { task -> task.dueDate }.toMutableList()
            })
        }
        val bundle = bundleOf("id" to task.id)
        findNavController().navigate(R.id.action_navigation_dashboard_to_task_details, bundle)

    }
}