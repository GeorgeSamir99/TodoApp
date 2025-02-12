package com.example.todoapp.ui.edit_task

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoapp.R
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.modal.Task
import com.example.todoapp.databinding.ActivityEditTaskBinding
import com.example.todoapp.ui.home.HomeActivity
import com.example.todoapp.ui.home.fregments.TasksFragment
import com.example.todoapp.ui.utlis.Constant
import com.example.todoapp.ui.utlis.clearTime
import com.example.todoapp.ui.utlis.setDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditTaskActivity : AppCompatActivity() {
    private lateinit var calendar: Calendar
    private lateinit var binding: ActivityEditTaskBinding
    lateinit var oldTask: Task
    private lateinit var newTask : Task
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         calendar =Calendar.getInstance()
        oldTask = intent.getParcelableExtra(Constant.TASK_KEY, Task::class.java)!!
         newTask = oldTask.copy()
        init()
        initListeners()

    }

    private fun init() {
        val title =oldTask.name
        binding.title.setText(title)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateString = oldTask.data?.let { dateFormat.format(it) } ?: "No Date"
        binding.selectDateTv.text = dateString
    }
    private fun initListeners() {
        binding.saveBtn.setOnClickListener {
            if (!isValidInputs()) return@setOnClickListener
                updateTaskIntoDatabase()
                navigateToHomeActivity()

        }
        binding.selectDateTv.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { view ,year,month,dayOfMonth ->

                    calendar.setDate(dayOfMonth, month, year)
                    calendar.clearTime()
                    binding.selectDateTv.text = "${dayOfMonth}/${month+1}/${year}"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)

            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }

    private fun navigateToHomeActivity() {
        val intent =Intent(this , HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun updateTaskIntoDatabase() {

        val title = binding.title.text.toString()
        val data = calendar.time
        newTask =oldTask.copy(name = title , data = data)
        TaskDatabase.getInstance().getTaskDao().updateTask(newTask)
        Toast.makeText(this, "Task Updated successfully", Toast.LENGTH_SHORT).show()
    }



    private fun isValidInputs(): Boolean {
        var isValid = true
        val title = binding.title.text

        val selectDate = binding.selectDateTv.text

        if (title?.trim().isNullOrEmpty()){
            binding.titleTil.error = getString(R.string.invalid_title)
            isValid = false
        }
        else{
            binding.titleTil.error = null
        }

        if (selectDate?.trim().isNullOrEmpty()){
            binding.selectDateTil.error = getString(R.string.require)
            isValid = false
        }
        else{
            binding.selectDateTil.error = null
        }

        return isValid
    }

}