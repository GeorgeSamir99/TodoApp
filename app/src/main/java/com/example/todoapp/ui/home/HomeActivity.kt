package com.example.todoapp.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityHomeBinding
import com.example.todoapp.ui.home.fregments.SettingsFragment
import com.example.todoapp.ui.home.fregments.TasksFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        serItemClickButtonNav()

    }


    private fun serItemClickButtonNav() {
        binding.todoBottomAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.navigation_settings -> {
                    showFragment(SettingsFragment())
                }
                R.id.navigation_lis -> {
                    showFragment(TasksFragment())

                }
            }

            return@setOnMenuItemClickListener true
        }
    }
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.todo_fragment_container,fragment)
            .commit()

    }

}