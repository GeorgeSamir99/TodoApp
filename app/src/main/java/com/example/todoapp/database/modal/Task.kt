package com.example.todoapp.database.modal

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,

    @ColumnInfo
    var name :String? = null,

    @ColumnInfo
    var isDone : Boolean = false,

    @ColumnInfo
    var data : Date? = null

):Parcelable
