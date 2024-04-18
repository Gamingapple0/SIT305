package com.example.lostandfoundapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lostandfoundapp.databinding.FragmentFormBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var google_lat_long by Delegates.notNull<LatLng>()
var google_address = ""
private var lastKnownLocation: Location? = null


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
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false

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
        fusedLocationProviderClient = activity?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!

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

        binding.editLocation.setOnClickListener{
            findNavController().navigate(R.id.action_form_to_location_picker)
        }

        val editableAddress = Editable.Factory.getInstance().newEditable(google_address)
        binding.editLocation.text = editableAddress

        binding.currentLocationButton.setOnClickListener{
            getLocationPermission()
            getDeviceLocation()
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
                val newItem = Item(binding.editName.text.toString(), binding.editPhone.text.toString(),getSelectedRadioText(binding.radioGroup),binding.editDescription.text.toString(),calendar,
                    google_lat_long, google_address)
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

        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    }


    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                this.activity?.let {
                    locationResult.addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            // Set the map's camera position to the current location of the device.
                            google_lat_long = LatLng(task.result.latitude, task.result.longitude)
                            // Obtain address from the last known location
                            val geocoder = activity?.let { it1 ->
                                Geocoder(
                                    it1,
                                    Locale.getDefault()
                                )
                            }
                            val addresses = geocoder?.getFromLocation(task.result.latitude, task.result.longitude, 1)
                            if (addresses != null) {
                                if (addresses.isNotEmpty()) {
                                    google_address = addresses[0]?.getAddressLine(0).toString()
                                } else {
                                    // If no address found, set it to an empty string or handle accordingly
                                    google_address = google_lat_long.toString()
                                }
                                val editableAddress = Editable.Factory.getInstance().newEditable(google_address)
                                binding.editLocation.text = editableAddress
                            }
//                                if (lastKnownLocation != null) {
//                                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                        LatLng(lastKnownLocation!!.latitude,
//                                            lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
//                                }
                        } else {
                            Log.d("TAG", "Current location is null. Using defaults.")
                            Log.e("TAG", "Exception: %s", task.exception)
                        }
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            }
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}