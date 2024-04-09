package com.example.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), Clickable {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var recyclerView: RecyclerView? = null
    private var recyclerView1: RecyclerView? = null
    private lateinit var binding:FragmentHomeBinding
    private lateinit var btnScrollLeft:Button
    private lateinit var btnScrollRight:Button

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerView
        recyclerView!!.layoutManager = GridLayoutManager(view.context, 2)

        recyclerView1 = binding.recyclerView1
        recyclerView1!!.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        val mainActivity = this

        recyclerView!!.adapter = CardAdapter(articles, mainActivity)
        recyclerView1!!.adapter = ImageAdapter(articles, mainActivity)

        btnScrollLeft = binding.btnScrollLeft
        btnScrollRight = binding.btnScrollRight

        val layoutManager1 = recyclerView1!!.layoutManager as LinearLayoutManager

        btnScrollLeft.setOnClickListener {
            val currentPosition = layoutManager1.findFirstVisibleItemPosition()
            if (currentPosition > 0) {
                recyclerView1!!.smoothScrollToPosition(currentPosition - 1)
            }
        }

        btnScrollRight.setOnClickListener {
            val currentPosition = layoutManager1.findLastVisibleItemPosition()
            if (currentPosition < (recyclerView1!!.adapter?.itemCount ?: 0) - 1) {
                recyclerView1!!.smoothScrollToPosition(currentPosition + 1)
            }
        }

        return view
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//
//        val view =  inflater.inflate(R.layout.fragment_home, container, false)
//        recyclerView = view.findViewById(R.id.recyclerView)
//        recyclerView!!.layoutManager = LinearLayoutManager(view.context)
//
//        recyclerView1 = view.findViewById(R.id.recyclerView1)
//        recyclerView1!!.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
//
//        binding = FragmentHomeBinding.inflate(layoutInflater)
//        val mainActivity = this
//        binding.recyclerView.apply{
//            layoutManager = GridLayoutManager(view.context, 2)
//            adapter = CardAdapter(destinations, mainActivity)
//        }
//
//        binding.recyclerView1.apply{
//            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
//            adapter = ImageAdapter(destinations, mainActivity)
//        }
//
////        val button = view.findViewById<Button>(R.id.homeButton)
////        button.setOnClickListener(){
////            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
////        }
//        return binding.root
//    }

    override fun onClick(articles: Articles) {
        super.onClick(articles)
        val bundle = bundleOf("id" to articles.id)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}