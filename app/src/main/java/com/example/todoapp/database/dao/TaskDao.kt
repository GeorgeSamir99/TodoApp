package com.example.todoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.database.modal.Task
import java.util.Date

@Dao
interface TaskDao {
    @Insert
    fun addTask(task : Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM Task")
    fun getAllTasks(task: Task):List<Task>

    @Query("SELECT * FROM Task WHERE data = :date")
    fun getTasksByDate(date : Date):List<Task>
}