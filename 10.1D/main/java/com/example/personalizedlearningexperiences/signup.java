package com.example.personalizedlearningexperiences;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.personalizedlearningexperiences.databinding.FragmentSignupBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signup extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentSignupBinding binding;

    public signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signup.
     */
    // TODO: Rename and change types and number of parameters
    public static signup newInstance(String param1, String param2) {
        signup fragment = new signup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentSignupBinding.inflate(inflater,container, false);

        binding.interestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Ash","Switch");
                if (binding.editUsername.getText().toString().isEmpty()) {
                    // Username is empty, show toast message
                    Toast.makeText(getActivity().getApplicationContext(), "Username is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (binding.editEmail.getText().toString().isEmpty()) {
                    // Email is empty, show toast message
                    Toast.makeText(getActivity().getApplicationContext(), "Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (binding.editConfirmEmail.getText().toString().isEmpty()) {
                    // Confirm Email is empty, show toast message
                    Toast.makeText(getActivity().getApplicationContext(), "Confirm Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (binding.editPassword.getText().toString().isEmpty()) {
                    // Password is empty, show toast message
                    Toast.makeText(getActivity().getApplicationContext(), "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (binding.editConfirmPassword.getText().toString().isEmpty()) {
                    // Confirm Password is empty, show toast message
                    Toast.makeText(getActivity().getApplicationContext(), "Confirm Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (binding.editPhone.getText().toString().isEmpty()) {
                    // Phone is empty, show toast message
                    Toast.makeText(getActivity().getApplicationContext(), "Phone Number is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!binding.editEmail.getText().toString().equals(binding.editConfirmEmail.getText().toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Emails must match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!binding.editPassword.getText().toString().equals(binding.editConfirmPassword.getText().toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Passwords must match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // All fields are filled, proceed with other actions

                // Create a bundle to pass data
                Bundle bundle = new Bundle();
                bundle.putString("username", binding.editUsername.getText().toString());
                bundle.putString("email", binding.editEmail.getText().toString());
                bundle.putString("confirmEmail", binding.editConfirmEmail.getText().toString());
                bundle.putString("password", binding.editPassword.getText().toString());
                bundle.putString("confirmPassword", binding.editConfirmPassword.getText().toString());
                bundle.putString("phone", binding.editPhone.getText().toString());

                // Navigate to the next fragment with the bundle
                Navigation.findNavController(v).navigate(R.id.action_signup_to_interests, bundle);

            }
        });

        return binding.getRoot();
    }
}