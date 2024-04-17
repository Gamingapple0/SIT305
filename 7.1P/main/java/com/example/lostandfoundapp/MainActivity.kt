package com.example.lostandfoundapp


import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.example.lostandfoundapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

lateinit var database: ItemDatabase

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

        database = Room.databaseBuilder(applicationContext, ItemDatabase::class.java, "itemDB")
            .fallbackToDestructiveMigration()
            .build()

        lifecycleScope.launch {


            database.itemDao().getItems().observe(this@MainActivity, Observer {
                // Sort the list by due date before assigning it to tasks
                items = it.toMutableList()

                Log.d("Ash", items.toString())
            })




    }


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}

