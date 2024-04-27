package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static String username = "";
    public static List<Chat> allChats = new ArrayList<Chat>();
    public static boolean displayChatValue = false;
    public static boolean isBotTurn = true;

    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostManager = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostManager.getNavController();



        Log.i("MainActivity", navController.toString());
        NavigationUI.setupActionBarWithNavController(this, navController);
    }
}