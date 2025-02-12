package com.example.todoapp.ui.home.fregments.callbacks

import com.example.todoapp.database.modal.Task

fun interface OnItemTaskClickListener {
    fun onClickListener(task : Task )
}