package com.example.todoapp.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityHomeBinding
import com.example.todoapp.ui.home.fregments.AddTaskFragment
import com.example.todoapp.ui.home.fregments.SettingsFragment
import com.example.todoapp.ui.home.fregments.TasksFragment
import com.example.todoapp.ui.home.fregments.callbacks.OnTodoAddListener

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private  val TasksFragment = TasksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fabOnClickListener()
        setItemClickButtonNav()
        binding.todoBottomNavigationView.selectedItemId = R.id.navigation_lis
    }

    private fun fabOnClickListener() {
        binding.addTaskFab.setOnClickListener {
            val fragment = AddTaskFragment()
            fragment.onTodoAddListener = OnTodoAddListener {
                TasksFragment.getTaskFromDatabase()

            }
            fragment.show(supportFragmentManager.beginTransaction(),"Add Task")


        }

    }


    private fun setItemClickButtonNav() {
        binding.todoBottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.navigation_settings -> {
                    println("Settings clicked")
                    showFragment(SettingsFragment())

                }
                R.id.navigation_lis -> {
                    println("Tasks clicked")
                    showFragment(TasksFragment)
                }
            }

            return@setOnItemSelectedListener true
        }
    }
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.todo_fragment_container,fragment)
            .addToBackStack(null)
            .commit()

    }

}