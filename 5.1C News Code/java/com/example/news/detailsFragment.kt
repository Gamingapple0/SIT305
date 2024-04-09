package com.example.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.FragmentDetailsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG="details"
class detailsFragment : Fragment(), Clickable {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var binding: FragmentDetailsBinding

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
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(view.context)
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val receivedObject = arguments?.getBundle("des")
        val id = arguments?.getInt("id")
//        Log.i("details",receivedObject.toString())
        Log.i("details", articles[id!!].toString())
        val destination = articles[id!!]
        binding.titleText.text = destination.title.toString()
        binding.mainImage.setImageResource(destination.img)
        binding.descriptionText.text = destination.desc.toString()
        val mainActivity = this
        binding.recyclerView.apply{
            layoutManager = GridLayoutManager(view.context, 1)
            adapter = RelatedAdapter(articles, mainActivity)
        }

        return binding.root
    }


    override fun onClick(articles: Articles) {
        super.onClick(articles)
        val bundle = bundleOf("id" to articles.id)
        findNavController().navigate(R.id.action_detailsFragment_self2, bundle)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            detailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}