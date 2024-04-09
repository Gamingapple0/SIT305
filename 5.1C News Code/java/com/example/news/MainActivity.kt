package com.example.news
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

private const val TAG="MainActivity"
class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostManager = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        populateArticles()
        navController = navHostManager.navController
        Log.i("MainActivity", navController.toString())
        setupActionBarWithNavController(navController)

    }

    private fun populateArticles(){
        val art1 = Articles("Mt Everest Report", R.drawable.everest,"Mt Everest 10 people climbed today!")
        articles.add(art1)

        val art2 = Articles("Bogo Sort Analysis", R.drawable.bogo,"Bogosort the best")
        articles.add(art2)

        val art3 = Articles("Nukes?", R.drawable.boom,"Nukes!!")
        articles.add(art3)

        val art4 = Articles("Obama vs Trump Boxing?", R.drawable.obvt,"Obama is allegedly fighting Donald Trump in a boxing fight")
        articles.add(art4)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }

}