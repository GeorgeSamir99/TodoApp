package com.example.todoapp.ui.home.fregments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.ui.utlis.clearTime
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.modal.Task
import com.example.todoapp.databinding.FragmentTasksBinding
import com.example.todoapp.ui.utlis.setDate
import com.example.todoapp.ui.edit_task.EditTaskActivity
import com.example.todoapp.ui.home.fregments.adapter.task_adapter.TasksAdapter
import com.example.todoapp.ui.home.fregments.adapter.viewConter.CustomWeekDayBinder
import com.example.todoapp.ui.home.fregments.callbacks.OnItemTaskClickListener
import com.example.todoapp.ui.utlis.Constant
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
import java.util.Date

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private lateinit var adapter: TasksAdapter
    private lateinit var calender:Calendar
    private lateinit var  calendarWeekDaysBinder : CustomWeekDayBinder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater ,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createCalendar()
        }
        calender = Calendar.getInstance()
        initRecyclerView()
        getTaskFromDatabase( )
    }

     private fun initRecyclerView() {

        adapter = TasksAdapter()
        binding.tasksListRec.adapter = adapter
         adapter.onDeleteClickListener = OnItemTaskClickListener { task ->
                 deleteTaskFromDatabase(task)
                 getTaskFromDatabase()
                 Toast.makeText(requireContext(), "Task Deleted", Toast.LENGTH_SHORT).show()
             }
         adapter.onTaskDoneClickListener = OnItemTaskClickListener { task ->
                updateDatabase(task)
                getTaskFromDatabase()

         }
         adapter.onTitleClickListener = OnItemTaskClickListener { task->
             navigateToEditeActivity(task)
         }

    }

    private fun navigateToEditeActivity(task :Task) {
        val intent = Intent(requireActivity(),EditTaskActivity::class.java)
        intent.putExtra(Constant.TASK_KEY,task)
        startActivity(intent)
    }

    private fun updateDatabase(task: Task){
            task.isDone = true
            TaskDatabase.getInstance().getTaskDao().updateTask(task)
    }


    private fun deleteTaskFromDatabase(task: Task){
         TaskDatabase.getInstance().getTaskDao().deleteTask(task)
    }

      @SuppressLint("NotifyDataSetChanged")
      fun getTaskFromDatabase() {
        val tasksList = TaskDatabase.getInstance().getTaskDao().getAllTasks()
        adapter.tasksList = tasksList
        adapter.notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getTaskByDate(date: Date){
        val tasksList= TaskDatabase.getInstance().getTaskDao().getTasksByDate(date)
        adapter.tasksList = tasksList
        adapter.notifyDataSetChanged()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun createCalendar() {
        val selectedColor = resources.getColor(R.color.primary_color , null)
        val unSelectedColor = resources.getColor(R.color.onBackground ,null)
        calendarWeekDaysBinder = object : CustomWeekDayBinder(selectedColor,unSelectedColor) {
            override var onDateSelected: ((WeekDay) -> Unit) = { weekDay ->
                val currentSelection = calendarWeekDaysBinder.selectedDate
                if (currentSelection == weekDay.date) {
                    calendarWeekDaysBinder.selectedDate = null
                    getTaskFromDatabase()
                    binding.weekCalendarView.notifyDateChanged(currentSelection)
                } else {
                    calendarWeekDaysBinder.selectedDate = weekDay.date
                    calender.setDate(weekDay.date.dayOfMonth ,weekDay.date.monthValue -1 ,weekDay.date.year)
                    calender.clearTime()
                    getTaskByDate(calender.time)
                    binding.weekCalendarView.notifyDateChanged(weekDay.date)
                    if (currentSelection != null) {
                        binding.weekCalendarView.notifyDateChanged(currentSelection)
                    }
                }
            }
        }
            binding.weekCalendarView.dayBinder = calendarWeekDaysBinder
            val currentDate = LocalDate.now()
            val currentMonth = YearMonth.now()
            val startDate = LocalDate.now()// Adjust as needed
            val endDate = currentMonth.plusMonths(100).atEndOfMonth() // Adjust as needed
            val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
            binding.weekCalendarView.setup(startDate = startDate, endDate = endDate, firstDayOfWeek = firstDayOfWeek)
            binding.weekCalendarView.scrollToWeek(currentDate)
        }

    }

