package com.example.lostandfoundapp

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.lostandfoundapp.databinding.FragmentItemDetailBinding
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [item_detail.newInstance] factory method to
 * create an instance of this fragment.
 */
class item_detail : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentItemDetailBinding

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
        binding = FragmentItemDetailBinding.inflate(layoutInflater, container, false)
        val id = arguments?.getInt("id")
        val currentItem = items.find { it.id == id }

        fun getActualLocation(latitude: Double, longitude: Double): String? {
            val geocoder = context?.let { Geocoder(it, Locale.getDefault()) }
            val addresses: MutableList<Address>? = geocoder?.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                return addresses[0].getAddressLine(0)
            } else {
                return null
            }
        }

        fun formatDateAgo(date: Calendar?): String {
            if (date == null) return ""

            val calendar = Calendar.getInstance()
            val currentTime = calendar.timeInMillis
            val providedTime = date.time.time

            val difference = currentTime - providedTime
            val daysAgo = difference / (1000 * 60 * 60 * 24) // Convert milliseconds to days

            return "$daysAgo days ago"
        }

        if (currentItem != null) {
            binding.textView7.text = currentItem.type + " " + currentItem.name
            val date = currentItem.date
            val formattedDate = formatDateAgo(date)
            binding.textView8.text = formattedDate
            binding.textView9.text = currentItem.location_address
            binding.textView10.text = currentItem.descriptption
            binding.textView11.text = currentItem.phone
        }

        binding.removeButton.setOnClickListener {
            lifecycleScope.launch {
                if (currentItem != null) {
                    database.itemDao().deleteItem(currentItem)
                }
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
         * @return A new instance of fragment item_detail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            item_detail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}