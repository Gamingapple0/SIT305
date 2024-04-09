package com.example.itube

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.itube.databinding.FragmentSignupBinding
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [signup.newInstance] factory method to
 * create an instance of this fragment.
 */
class signup : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentSignupBinding

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

        binding = FragmentSignupBinding.inflate(inflater, container, false)
        val usernames = mutableListOf<String>()
        for(user in users){
            usernames.add(user.username.toString())
        }


        binding.createAccountButton.setOnClickListener{
            if (binding.editUserSignup.text.toString() !in usernames){
                if (binding.editPassSignup.text.toString() == binding.editConfirmPass.text.toString()){
                    if (binding.editPassSignup.text.toString() != "" && binding.editUserSignup.text.toString() != "" && binding.editName.text.toString() != ""){
                        val newPlaylist: MutableList<String> = ArrayList()
                        val newUser = User(binding.editUserSignup.text.toString(),binding.editPassSignup.text.toString(),newPlaylist)
                        lifecycleScope.launch {
                            database.userDao().insertUser(newUser)
                            findNavController().navigate(R.id.action_signup_to_login)
                        }
                    }
                    else{
                        Toast.makeText(this.context,"All fields must be filled",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this.context,"Passwords don't match",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this.context,"Username already exits",Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment signup.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            signup().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}