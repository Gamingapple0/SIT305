package com.example.personalizedlearningexperiences;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import static com.example.personalizedlearningexperiences.MainActivity.currentUser;
import static com.example.personalizedlearningexperiences.MainActivity.currentUserIndex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personalizedlearningexperiences.databinding.FragmentLoginBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentLoginBinding binding;
    private UserViewModel userViewModel;
    private List<User> users = new ArrayList<>();

    public login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment login.
     */
    // TODO: Rename and change types and number of parameters
    public static login newInstance(String param1, String param2) {
        login fragment = new login();
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
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // Initialize NavController
//        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // Observe the list of users
//        userViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> userList) {
//                // Update the list of users in the fragment
//                users.clear();
//                users.addAll(userList);
//                // Perform any necessary actions with the updated user list
//                Log.d("AshLogin", "Users: " + users);
//            }
//        });
        // Set OnClickListener for signupButton
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the desired destination
                Navigation.findNavController(v).navigate(R.id.action_login_to_signup);
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                List<String> usernames = new ArrayList<>();
                LiveData<List<User>> usersLiveData = userViewModel.getUsers();
                users = usersLiveData.getValue();

                for (int index = 0; index < users.size(); index++) {
                    User user = users.get(index);
                    if (user.getUsername() != null) {
                        usernames.add(user.getUsername());
                        if (binding.editUsername.getText().toString().equals(user.getUsername())){
                            currentUser = user;
                        }
                    }
                }

                Log.d("AshLogin", "Usernames: " + usernames);
                if (usernames.contains(binding.editUsername.getText().toString())) {
                    User user = currentUser;
                    Log.i("AshLogin",user.getPassword());
                    Log.i("AshLogin",binding.editPassword.getText().toString());
                    if (user.getPassword().equals(binding.editPassword.getText().toString())) {
                        currentUser = user;
                        Log.i("AshLogin","Passed");
                        Navigation.findNavController(v).navigate(R.id.action_login_to_dashboard);
                    }
                }


            }
        });

        return binding.getRoot();
    }

}