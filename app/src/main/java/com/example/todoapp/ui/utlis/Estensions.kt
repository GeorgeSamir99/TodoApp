package com.example.todoapp.ui.utlis

import java.util.Calendar

fun Calendar.setDate(dayOfMoth : Int , month : Int , year:Int){

    set(Calendar.DAY_OF_MONTH , dayOfMoth)
    set(Calendar.MONTH , month)
    set(Calendar.YEAR , year)
}
fun Calendar.clearTime(){
    set(Calendar.HOUR,0)
    set(Calendar.MINUTE,0)
    set(Calendar.SECOND,0)
    set(Calendar.MILLISECOND,0)
}