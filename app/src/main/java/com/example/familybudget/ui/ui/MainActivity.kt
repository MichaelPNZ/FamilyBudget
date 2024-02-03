package com.example.familybudget.ui.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.familybudget.R
import com.example.familybudget.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        binding.btnWalletSelection.setOnClickListener {
            // Handle account selection button click
            // For example, open the account selection screen
            Toast.makeText(this@MainActivity, "Account Selection clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnActionMenu.setOnClickListener {
            // Handle action menu button click
            // For example, open the action menu
            Toast.makeText(this@MainActivity, "Action Menu clicked", Toast.LENGTH_SHORT).show()
        }
    }
}