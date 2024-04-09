package com.example.taskmanager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.example.taskmanager.databinding.FragmentTaskDetailsBinding
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [task_details.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class task_details : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentTaskDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)
        binding = FragmentTaskDetailsBinding.inflate(layoutInflater)
        val id = arguments?.getInt("id")
        if (id != null){

            lifecycleScope.launch {
                database.taskDao().getTasks().observe(viewLifecycleOwner, Observer {
                    // Sort the list by due date before assigning it to tasks
                    tasks = it.sortedBy { task -> task.dueDate }.toMutableList()
                })
            }
            Log.i("details", tasks.toString())
            Log.i("details", id.toString())
            val task = tasks.find { it.id == id }
            val note = tasks[id]
            Log.i("details",note.toString())
            if (task != null){
                binding.editTitle.setText(task.title);
                binding.editDescription.setText(task.desc)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(task.dueDate?.time ?: "")
                binding.editTextDate.setText(formattedDate)
            }

        }

        binding.saveButton.setOnClickListener(){
            if (id != null){
                tasks[id].title = binding.editTitle.text.toString()
                tasks[id].desc = binding.editDescription.text.toString()
                val calendar = Calendar.getInstance()
                val dateString = binding.editTextDate.text.toString()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = binding.editTextDate.text.toString().let { dateString ->
                    dateFormat.parse(dateString)?.let { calendar ->
                        dateFormat.format(calendar.time)
                    } ?: ""
                }

                try {
                    val date = dateFormat.parse(dateString)
                    calendar.time = date
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

// Assign the Calendar object to the dueDate property
                tasks[id].dueDate = calendar
                lifecycleScope.launch {
                    database.taskDao().updateTask(tasks[id])
                }
            }
            else{
//                tasks.add(Task(binding.editTitle.text.toString(), binding.editDescription.text.toString()))
                val dateString = binding.editTextDate.text.toString()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

// Parse the dateString into a Date object
                val date: Date? = dateFormat.parse(dateString)

// Create a Calendar instance and set its time to the parsed date
                val calendar = Calendar.getInstance().apply {
                    if (date != null) {
                        time = date
                    }
                }

                try {
                    val date = dateFormat.parse(dateString)
                    calendar.time = date
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                lifecycleScope.launch {
                    database.taskDao().insertTask(Task(binding.editTitle.text.toString(),binding.editDescription.text.toString(),calendar))
                    binding.editTitle.setText("")
                    binding.editDescription.setText("")
                    binding.editTextDate.setText("")
                }
            }
        }

        binding.deleteButton.setOnClickListener(){
            if(id != null){
//                tasks.removeAt(id)
                lifecycleScope.launch{
                    database.taskDao().deleteTask(tasks[id])
                }
            }
//            findNavController(it).navigate(R.id.action_note_details_to_home1)
        }

        //        binding.title.text = Editable.Factory.getInstance().newEditable(note.title.toString())
//        binding.description.text = Editable.Factory.getInstance().newEditable(note.desc.toString())

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment task_details.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            task_details().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}