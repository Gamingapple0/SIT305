package com.example.taskmanager.ui.addTask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.Task
import com.example.taskmanager.database
import com.example.taskmanager.databinding.AddTaskBinding
import com.example.taskmanager.databinding.FragmentTaskDetailsBinding
import com.example.taskmanager.tasks
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTaskFragment : Fragment() {

    private var _binding: AddTaskBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addTaskViewModel =
            ViewModelProvider(this).get(AddTaskViewModel::class.java)

        _binding = AddTaskBinding.inflate(inflater, container, false)

        val id = arguments?.getInt("id")
        if (id != null){
            Log.i("details", tasks[id!!].toString())
            val note = tasks[id!!]
            binding.editTitle.setText(note.title);
            binding.editDescription.setText(note.desc)
            binding.editTextDate.setText(note.dueDate.toString())
        }

        binding.saveButton.setOnClickListener(){
            if (id != null){
//                tasks[id].title = binding.editTitle.text.toString()
//                tasks[id].desc = binding.editDescription.text.toString()
            }
            else{
//                tasks.add(Task(binding.editTitle.text.toString(), binding.editDescription.text.toString()))
                val calendar = Calendar.getInstance()
                val dateString = binding.editTextDate.text.toString()
                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                try {
                    val date = dateFormat.parse(dateString)
                    calendar.time = date
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                lifecycleScope.launch {
                    database.taskDao().insertTask(Task(binding.editTitle.text.toString(), binding.editDescription.text.toString(),calendar))
                    binding.editTitle.setText("")
                    binding.editDescription.setText("")
                    binding.editTextDate.setText("")
                }
            }
        }

        binding.deleteButton.setOnClickListener(){
            if(id != null){
                tasks.removeAt(id)
            }
//            findNavController(it).navigate(R.id.action_note_details_to_home1)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}