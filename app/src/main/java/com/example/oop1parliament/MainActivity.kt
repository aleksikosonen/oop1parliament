package com.example.oop1parliament

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.oop1parliament.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        //val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        //Variables needed for bottom navigation
        val bottomNavigation = binding.bottomNavigation
        val navController = findNavController(R.id.myNavHostFragment)

        //Listener for bottom navigation to select which fragment is loaded
        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.ic_home -> { navController.navigate(R.id.action_global_titleFragment) }
                R.id.ic_person -> { navController.navigate(R.id.action_global_partySelect) }
                R.id.ic_info -> { navController.navigate(R.id.action_global_appDetailsFragment) }
            }
            true
        }

        bottomNavigation.setOnNavigationItemReselectedListener {
            //Empty to prevent reload of fragment
        }
    }
}
