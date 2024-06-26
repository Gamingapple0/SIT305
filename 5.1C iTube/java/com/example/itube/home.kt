package com.example.itube

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.itube.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [home.newInstance] factory method to
 * create an instance of this fragment.
 */
class home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding
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
        binding.addToPlaylistButton.setOnClickListener{
            val url = binding.editUrl.text.toString()
            if (url != ""){
                lifecycleScope.launch {
                    val currentUser = users[index]
                    val updatedPlaylist = currentUser.playlist.toMutableList()
                    updatedPlaylist.add(url)
                    currentUser.playlist = updatedPlaylist.toList()
                    database.userDao().updateUser(currentUser)
                }
                Toast.makeText(this.context,"Added to Playlist",Toast.LENGTH_SHORT).show()
            }

        }

        binding.myPlaylistButton.setOnClickListener{
            findNavController().navigate(R.id.action_home2_to_playlist)
        }

        binding.playButton.setOnClickListener{
            val url = binding.editUrl.text.toString()
            if (url != ""){
                val bund = bundleOf("url" to url)
                findNavController().navigate(R.id.action_home2_to_player, bund)
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
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}