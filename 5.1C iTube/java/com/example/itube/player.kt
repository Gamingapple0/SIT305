package com.example.itube

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.itube.databinding.FragmentPlayerBinding
import com.example.itube.databinding.FragmentPlaylistBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [player.newInstance] factory method to
 * create an instance of this fragment.
 */
class player : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPlayerBinding

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
        binding = FragmentPlayerBinding.inflate(inflater,container,false)
        val url = arguments?.getString("url")
        var split = ""
        if (url != null) {
            split = url.split("be/")[1]
            split = "https://www.youtube.com/embed/" + split
            Log.i("Ash",split)
        }
//        val mappedUrl = url?.map { if (it == '/') '\\' else it }?.joinToString("")
//        val map = mapOf("original" to url, "mapped" to mappedUrl)
//        Log.i("Ash", mappedUrl.toString())


        val webview = binding.webView
        val video = "<iframe width=\"100%\" height=\"100%\" src=\"${split}\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
//        "https://youtu.be/ufzJ697y5eo?si=p4-y6I5NSj_olJ6M"
        webview.loadData(video,"text/html","utf-8")
        webview.settings.javaScriptEnabled = true
        webview.webChromeClient = WebChromeClient()
        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment player.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            player().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}