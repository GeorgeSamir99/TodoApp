package com.example.todoapp.database.modal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,

    @ColumnInfo
    var name :String? = null,

    @ColumnInfo
    var isDone : Boolean = false,

    @ColumnInfo
    var data : Long? = null

)
