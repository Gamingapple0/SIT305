package com.example.personalizedlearningexperiences;

import static com.example.personalizedlearningexperiences.MainActivity.database;
import static com.example.personalizedlearningexperiences.MainActivity.interestsList;
import static com.example.personalizedlearningexperiences.MyInterestViewAdapter.chosenInterests;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personalizedlearningexperiences.databinding.FragmentInterestsBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link interests#newInstance} factory method to
 * create an instance of this fragment.
 */
public class interests extends Fragment implements Clickable{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentInterestsBinding binding;
    private RecyclerView recyclerView;

    String username = "";
    String email = "";
    String confirmEmail = "";
    String password = "";
    String confirmPassword = "";
    String phone = "";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public interests() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment interests.
     */
    // TODO: Rename and change types and number of parameters
    public static interests newInstance(String param1, String param2) {
        interests fragment = new interests();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            email = bundle.getString("email");
            confirmEmail = bundle.getString("confirmEmail");
            password = bundle.getString("password");
            confirmPassword = bundle.getString("confirmPassword");
            phone = bundle.getString("phone");
            // Do something with the data

            Log.i("AshI", "Username: " + username);
            Log.i("AshI", "Email: " + email);
            Log.i("AshI", "Confirm Email: " + confirmEmail);
            Log.i("AshI", "Password: " + password);
            Log.i("AshI", "Confirm Password: " + confirmPassword);
            Log.i("AshI", "Phone: " + phone);
        }
        binding = FragmentInterestsBinding.inflate(inflater, container, false);

        recyclerView = binding.getRoot().findViewById(R.id.allInterests);
        recyclerView.setAdapter(new MyInterestViewAdapter(interestsList, this));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    protected Void doInBackground(Void... voids) {
                        // Perform database insertion
                        User newUser = new User(username, password, phone, email, chosenInterests, "Basic", new ArrayList<Question>());
                        database.userDao().insertUser(newUser);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        // Navigate to the next screen after the database operation is complete
                        Navigation.findNavController(v).navigate(R.id.action_interests_to_login);
                    }
                }.execute();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onClick(String selected) {
        // Handle item click here
        Log.i("Ash2",selected);
    }
}