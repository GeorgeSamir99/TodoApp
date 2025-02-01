package com.example.todoapp

import android.app.Application
import com.example.todoapp.database.TaskDatabase

class TaskApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        TaskDatabase.init(this)
    }
}