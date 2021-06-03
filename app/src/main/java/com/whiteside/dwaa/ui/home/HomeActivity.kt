package com.whiteside.dwaa.ui.home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.whiteside.dwaa.R
import com.whiteside.dwaa.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bind: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
        setupWithBottom()
    }

    private fun setupWithBottom() {
        bind.navView.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }
        bind.navView.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }
}
