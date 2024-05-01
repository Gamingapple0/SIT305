package com.example.personalizedlearningexperiences;

import static com.example.personalizedlearningexperiences.MainActivity.currentUser;
import static com.example.personalizedlearningexperiences.MainActivity.generatedQuestions;
import static com.example.personalizedlearningexperiences.MainActivity.resultUrls;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personalizedlearningexperiences.databinding.FragmentQuestionsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import static com.example.personalizedlearningexperiences.MainActivity.database;
import static com.example.personalizedlearningexperiences.MyInterestViewAdapter.chosenInterests;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link questions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class questions extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentQuestionsBinding binding;
    private MyQuestionViewAdapter adapter;
    private List<Question> questionList;

    public questions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment questions.
     */
    // TODO: Rename and change types and number of parameters
    public static questions newInstance(String param1, String param2) {
        questions fragment = new questions();
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
        binding = FragmentQuestionsBinding.inflate(inflater,container,false);

        // Initialize RecyclerView and data
        questionList = new ArrayList<>();

        // Add sample questions
//        List<String> options1 = Arrays.asList("Option 1", "Option 2", "Option 3");
//        Question question1 = new Question("Correct answer 1", options1, "Question 1", "", "Topic 1", Calendar.getInstance(), "");
//        questionList.add(question1);
//
//        Question question2 = new Question("Correct answer 2", options1, "Question 2", "", "Topic 2", Calendar.getInstance(), "");
//        questionList.add(question2);
//
//        Question question3 = new Question("Correct answer 2", options1, "Question 3", "", "Topic 3", Calendar.getInstance(), "");
//        questionList.add(question3);

        Log.i("AshQues", questionList.toString());

        // Set up RecyclerView
        adapter = new MyQuestionViewAdapter(generatedQuestions);
        binding.questionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.questionRecyclerView.setAdapter(adapter);

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                User updatedUser = currentUser;
                updatedUser.getAllQuestions().addAll(generatedQuestions);

//                updatedUser.setAllQuestions(generatedQuestions);

                new AsyncTask<Void, Void, Void>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    protected Void doInBackground(Void... voids) {
                        // Perform database insertion
                        database.userDao().updateUser(updatedUser);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        // Navigate to the next screen after the database operation is complete
//                        Navigation.findNavController(v).navigate(R.id.action_interests_to_login);
                    }
                }.execute();

                Navigation.findNavController(v).navigate(R.id.action_questions_to_results);

                Log.i("AshResUrls",resultUrls.values().toString());
            }
        });

        return binding.getRoot();
    }
}