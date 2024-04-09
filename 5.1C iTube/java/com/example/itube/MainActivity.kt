package com.example.itube

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.example.itube.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


lateinit var database: UserDatabase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostManager = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostManager.navController
        Log.i("MainActivity", navController.toString())
        setupActionBarWithNavController(navController)


        database = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "userDB")
            .fallbackToDestructiveMigration()
            .build()

        lifecycleScope.launch {
            database.userDao().getUsers().observe(this@MainActivity, Observer {
                // Sort the list by due date before assigning it to tasks
                users = it.toMutableList()

                Log.d("Ash", users.toString())
            })
//            val playlist: MutableList<String> = ArrayList()
//            playlist.add("Beast")
//            playlist.add("Pew")

//            val user = User("Admin", "admin123", playlist)
//            database.userDao().insertUser(user)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}