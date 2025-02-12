package com.example.todoapp.ui.home.fregments.adapter.viewConter


import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.example.todoapp.databinding.ItemDayBinding
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
abstract class CustomWeekDayBinder(
    private val selectedColor : Int,
    private val unSelectedColor : Int,
    ): WeekDayBinder<DayViewContainer> {
    abstract var onDateSelected:((WeekDay)->Unit)
    var selectedDate: LocalDate? = null
    override fun bind(container: DayViewContainer, data: WeekDay) {
        container.weekDay.text = data.date.dayOfWeek.getDisplayName(
            /* style = */
            TextStyle.SHORT,
            /* locale = */
            Locale.getDefault())
        container.monthDay.text = "${data.date.dayOfMonth}"
        if(selectedDate == data.date){
            container.monthDay.setTextColor(selectedColor)
            container.weekDay.setTextColor(selectedColor)
        }
        else{
            container.monthDay.setTextColor(unSelectedColor)
            container.weekDay.setTextColor(unSelectedColor)
        }
        container.binding.root.setOnClickListener {
            onDateSelected(data)
        }
    }
    override fun create(view: View): DayViewContainer {
        return DayViewContainer(view) }
}

class DayViewContainer(view : View) : ViewContainer(view) {

        val binding = ItemDayBinding.bind(view)
       val weekDay  = binding.weekDay
        val monthDay = binding.monthDay

}