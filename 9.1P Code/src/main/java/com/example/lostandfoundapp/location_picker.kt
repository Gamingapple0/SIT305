package com.example.lostandfoundapp

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lostandfoundapp.databinding.FragmentLocationPickerBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.coroutines.launch
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [location_picker.newInstance] factory method to
 * create an instance of this fragment.
 */
class location_picker : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentLocationPickerBinding
    private lateinit var map:GoogleMap
    private lateinit var autoComplete:AutocompleteSupportFragment
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
//        Places.initialize(context, R.string.my_map_api_key.toString())
//        autoComplete = childFragmentManager.findFragmentById(R.id.auto_complete_fragment) as AutocompleteSupportFragment
//        autoComplete.setPlaceFields(listOf(Place.Field.ID,Place.Field.ADDRESS,Place.Field.LAT_LNG))
//        autoComplete.setOnPlaceSelectedListener(object :PlaceSelectionListener{
//            override fun onError(p0: Status) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onPlaceSelected(place: Place) {
//                map.addMarker(MarkerOptions().position(place.latLng).title(place.address))
//            }
//
//        })

        binding = FragmentLocationPickerBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Places.initialize(context, "AIzaSyA5E-5iboeHICVyYQFQIBAVpcmMOR-U5vE")
        Log.i("Ash", childFragmentManager.toString())
        autoComplete = childFragmentManager.findFragmentById(R.id.auto_complete_fragment) as AutocompleteSupportFragment
        autoComplete.setPlaceFields(listOf(Place.Field.ID,Place.Field.ADDRESS,Place.Field.LAT_LNG))
        autoComplete.setOnPlaceSelectedListener(object :PlaceSelectionListener{
            override fun onError(p0: Status) {
//                Toast.makeText(context,"OOps",Toast.LENGTH_SHORT)
                Log.i("Ash",p0.toString())
            }

            override fun onPlaceSelected(place: Place) {
                google_lat_long = place.latLng
                google_address = place.address

                findNavController().navigate(R.id.action_location_picker_to_form)
//                map.addMarker(MarkerOptions().position(place.latLng).title(place.address))
            }

        })
//        val mapSearchView = binding.searchView

//        mapSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                val locString = mapSearchView.query.toString()
//                lateinit var addresses:List<Address>
//                if (locString.isNotBlank()) {
//                    val geocoder = context?.let { Geocoder(it) }
//                    try {
//                        addresses = geocoder?.getFromLocationName(locString, 1)!!
//                        if (addresses.isNotEmpty()) {
//                            val address = addresses[0]
//                            val latLng = LatLng(address.latitude, address.longitude)
//                            map.addMarker(MarkerOptions().position(latLng).title(locString))
//                            // Use latLng
//                        }
//                        // Use the addresses list
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//
//                }
//
//
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // Handle query text change
//                return false
//            }
//        })
//
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment location_picker.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            location_picker().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // Add a marker in Kyoto and move the camera

        for (item in items){
            map.addMarker(MarkerOptions().position(item.location).title(item.location_address))
        }

//        val kyoto = LatLng(35.00116, 135.7681)
//        googleMap.addMarker(MarkerOptions().position(kyoto).title("Marker in Kyoto"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kyoto))
//        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
    }
}