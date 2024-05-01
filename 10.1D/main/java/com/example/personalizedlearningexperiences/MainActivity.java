package com.example.personalizedlearningexperiences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private NavController navController;
    public static UserDatabase database;
    private List<User> users = new ArrayList<>();
    private UserViewModel userViewModel;
    public static User currentUser;
    public static int currentUserIndex;
    public static List<Question> generatedQuestions = new ArrayList<>();
    public static HashMap<Integer, String> resultUrls = new HashMap<>();


//    Task<Boolean> task = paymentsClient.isReadyToPay(readyToPayRequest);
//    task.addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
//        @Override
//        public void onComplete(@NonNull Task<Boolean> completeTask) {
//            if (completeTask.isSuccessful()) {
//                showGooglePlayButton(completeTask.getResult());
//            } else {
//                // Handle the case where the task is not successful
//            }
//        }
//    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostManager = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostManager.getNavController();
        Log.i("MainActivity", navController.toString());
        NavigationUI.setupActionBarWithNavController(this, navController);

        database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "userDB")
                .fallbackToDestructiveMigration()
                .build();

        userViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                users = userList;
                Log.d("Ash", users.toString());
            }
        });



//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,"http://10.0.2.2:5000/getQuiz?topic=Movies", null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                parseJsonResponse(response);
//                Log.i("AshRes",response.toString());
//            }
//
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("AshRes", String.valueOf(error));
//            }
//        });
//
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                120000, // Timeout in milliseconds (2 minutes)
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        Volley.newRequestQueue(this).add(request);


    }



    public static List<String> interestsList = new ArrayList<String>() {{
        add("Anime");
        add("Music");
        add("Movies");
        add("TV Shows");
    }};
    @Override
    public boolean onSupportNavigateUp(){
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}