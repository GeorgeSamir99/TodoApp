package com.example.todoapp.ui.home.fregments

import android.app.DatePickerDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.todoapp.R
import com.example.todoapp.ui.utlis.clearTime
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.modal.Task
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.ui.utlis.setDate
import com.example.todoapp.ui.home.fregments.callbacks.OnTodoAddListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentAddTaskBinding
     var onTodoAddListener: OnTodoAddListener ? = null
    private lateinit var calendar: Calendar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater , container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        initListeners()
    }



    private fun initListeners() {
        binding.addTaskBtn.setOnClickListener {
            if (isValidInputs())
                addTaskIntoDatabase()
                return@setOnClickListener

        }
        binding.selectDateTv.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                {
                    view ,year,month,dayOfMonth ->
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

    private fun addTaskIntoDatabase() {
        val title = binding.title.text.toString()
        val data = calendar.time
        val task = Task(name = title, data = data)
        TaskDatabase.getInstance().getTaskDao().addTask(task)
        onTodoAddListener?.onTodoListener()
        dismissAllowingStateLoss()
        Toast.makeText(requireContext(), "task add successfully", Toast.LENGTH_SHORT).show()
    }


    private fun isValidInputs(): Boolean {
        var isValid = true
        val title = binding.title.text
        val description = binding.description.text
        val selectDate = binding.selectDateTv.text

        if (title?.trim().isNullOrEmpty()){
            binding.titleTil.error = getString(R.string.invalid_title)
            isValid = false
        }
        else{
            binding.titleTil.error = null
        }
        if (description?.trim().isNullOrEmpty()){
            binding.descriptionTil.error = getString(R.string.invalid_description)
            isValid = false
        }
        else{
            binding.descriptionTil.error = null
        }
        if (selectDate?.trim().isNullOrEmpty()){
            binding.selectDateTil.error = "require"
            isValid = false
        }
        else{
            binding.selectDateTil.error = null
        }

        return isValid
    }
}