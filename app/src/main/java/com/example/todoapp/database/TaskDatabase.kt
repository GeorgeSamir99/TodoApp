package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.database.dao.TaskDao
import com.example.todoapp.database.modal.Task
import com.example.todoapp.database.typeConverters.Converters

@Database(entities = [Task::class], version = 1)
@TypeConverters(value = [Converters::class])
abstract class TaskDatabase : RoomDatabase(){
    abstract fun getTaskDao(): TaskDao

    companion object{
        private var DATABASE_INSTANCE : TaskDatabase? = null

        fun init(applicationContext: Context){
            if (DATABASE_INSTANCE == null){
                DATABASE_INSTANCE = Room.databaseBuilder(
                    applicationContext,
                    TaskDatabase::class.java,
                    "Tasks Database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }

        fun getInstance() : TaskDatabase{

            return DATABASE_INSTANCE !!

        }


    }

}