package com.example.lostandfoundapp

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.lifecycleScope
import com.example.lostandfoundapp.databinding.FragmentFormBinding
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [form.newInstance] factory method to
 * create an instance of this fragment.
 */
class form : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFormBinding

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
        binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        fun getSelectedRadioText(radioGroup: RadioGroup): String? {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            return if (selectedRadioButtonId != -1) {
                val selectedRadioButton = radioGroup.findViewById<RadioButton>(selectedRadioButtonId)
                selectedRadioButton.text.toString()
            } else {
                null
            }
        }

        fun String.toLocation(): Location? {
            val location = Location("")
            val latLong = this.split(",")
            if (latLong.size == 2) {
                try {
                    location.latitude = latLong[0].toDouble()
                    location.longitude = latLong[1].toDouble()
                    return location
                } catch (e: NumberFormatException) {
                    // Handle invalid format
                }
            }
            return null
        }


        binding.saveButton.setOnClickListener{
            val calendar = Calendar.getInstance()
            val dateString = binding.editDate.text.toString()
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            try {
                val date = dateFormat.parse(dateString)
                calendar.time = date
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            lifecycleScope.launch {
                val newItem = Item(binding.editName.text.toString(), binding.editPhone.text.toString(),getSelectedRadioText(binding.radioGroup),binding.editDescription.text.toString(),calendar,binding.editLocation.text.toString().toLocation())
                database.itemDao().insertItem(newItem)
                binding.editName.setText("")
                binding.editDescription.setText("")
                binding.editDate.setText("")
                binding.editPhone.setText("")
                binding.editLocation.setText("")
            }
        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment form.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            form().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}