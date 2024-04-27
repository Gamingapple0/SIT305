package com.example.chatbot;

import static com.example.chatbot.MainActivity.allChats;
import static com.example.chatbot.MainActivity.displayChatValue;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chatbot.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentHomeBinding binding;
    private MyChatViewAdapter adapter;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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

    private String parseBotResponse(JSONObject response) throws JSONException {
        return response.getString("chat");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater,container, false);
        adapter = new MyChatViewAdapter();
        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.chatRecyclerView.setAdapter(adapter);

        binding.sendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat newChat1 = new Chat("",true);
                allChats.add(newChat1);

                Chat newChat = new Chat(binding.chatInputBox.getText().toString(),false);
                allChats.add(newChat);

                adapter.notifyItemInserted(allChats.size()-1);

                // Encode question and answer parameters
                String encodedParams = null;
                try {
                    encodedParams = "prompt=" + URLEncoder.encode(binding.chatInputBox.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                // Construct the full URL with encoded parameters
                String baseUrl = "http://10.0.2.2:5000/getChat?";
                String fullUrl = baseUrl + encodedParams;
                Log.i("AshUrl",fullUrl);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Chat newBotIcon = new Chat("",true);
                            Chat newBotChat = new Chat(parseBotResponse(response),false);

                            allChats.add(newBotIcon);
                            allChats.add(newBotChat);

                            adapter.notifyItemInserted(allChats.size()-1);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.i("AshRes", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("AshRes", String.valueOf(error));
                    }
                });

                request.setRetryPolicy(new DefaultRetryPolicy(
                        120000, // Timeout in milliseconds (2 minutes)
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                Volley.newRequestQueue(getContext()).add(request);


                adapter.notifyItemInserted(allChats.size()-1);

//                adapter.notifyItemInserted(allChats.size()-1);
            }
        });

        return binding.getRoot();
    }
}