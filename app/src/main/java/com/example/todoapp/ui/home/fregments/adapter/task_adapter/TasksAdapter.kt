package com.example.todoapp.ui.home.fregments.adapter.task_adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.R
import com.example.todoapp.database.modal.Task
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.ui.home.fregments.callbacks.OnItemTaskClickListener
import com.zerobranch.layout.SwipeLayout
import com.zerobranch.layout.SwipeLayout.SwipeActionsListener
import java.text.SimpleDateFormat
import java.util.Locale

class TasksAdapter(var tasksList : List<Task>? = null) : Adapter<TasksAdapter.TasksViewHolder>(){
    var onDeleteClickListener: OnItemTaskClickListener? = null
    var onTaskDoneClickListener: OnItemTaskClickListener? = null
    var onTitleClickListener : OnItemTaskClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TasksViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tasksList?.size ?: 0
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = tasksList?.get(position) ?: return

       holder.isDoneState(task.isDone)
        holder.bind(task)
        onTaskDoneClickListener?.let {
            holder.binding.btnTaskIsDone.setOnClickListener {
                onTaskDoneClickListener?.onClickListener(task)
            }
        }
        onTitleClickListener?.let {
            holder.binding.title.setOnClickListener {
                onTitleClickListener?.onClickListener(task)
            }

        }
        onDeleteClickListener?.let {
            holder.binding.swipeLayout.setOnActionsListener(object :SwipeActionsListener{
                override fun onOpen(direction: Int, isContinuous: Boolean) {
                    if (direction == SwipeLayout.RIGHT){
                        holder.binding.leftView.setOnClickListener {
                            onDeleteClickListener?.onClickListener(task )
                        }
                    }
                }

                override fun onClose() {

                }
            })

        }

    }



   inner class TasksViewHolder( val binding: ItemTaskBinding) :ViewHolder(binding.root){
        fun bind(task : Task){
            binding.title.text = task.name
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateString = task.data?.let { dateFormat.format(it) } ?: "No Date"
            binding.time.text = dateString
        }
       fun isDoneState(isDone :Boolean){
           if (isDone){
               binding.title.setTextColor(Color.GREEN)
               binding.btnTaskIsDone.setBackgroundResource(R.drawable.ic_done)
               binding.draggingBar.setBackgroundColor(Color.GREEN)

           } else{
               binding.title.setTextColor(ContextCompat.getColor(itemView.context,R.color.primary_color))
               binding.draggingBar.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.primary_color))
               binding.btnTaskIsDone.setBackgroundResource(R.drawable.check_mark)
           }
       }
    }

}