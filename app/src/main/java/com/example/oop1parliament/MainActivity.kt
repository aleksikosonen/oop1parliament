package com.example.oop1parliament

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.oop1parliament.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        /*val navController = findNavController(R.id.myNavHostFragment)

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_home -> {
                    navController.navigate(R.id.action_global_titleFragment)
                }
                R.id.ic_list -> {navController.navigate(R.id.action_global_partySelect)}
                R.id.ic_person -> {navController.navigate(R.id.action_global_partyMembersFragment)}
                }
            //return@OnNavigationItemSelectedListener true
            true
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }*/

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navController: NavController = findNavController(R.id.myNavHostFragment)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.ic_home -> {
                    navController.navigate(R.id.action_global_titleFragment)
                }
                R.id.ic_list -> {
                    navController.navigate(R.id.action_global_partySelect)
                }
                R.id.ic_person -> {
                    navController.navigate(R.id.action_global_memberDetailsFragment)
                }
            }
            true
        }

        bottomNavigation.setOnNavigationItemReselectedListener {
            //Empty to prevent reload of fragment
        }
    }
}
