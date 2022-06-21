package uok.sm.coding.todoapp.viewmodel

import androidx.lifecycle.LiveData
import uok.sm.coding.todoapp.model.Task

interface TaskCallback {

    fun showDate(date : String, dayName : String,dayPosition : Long){}
    fun showError(errorMessage : String){}
    fun taskSaved(){}
    fun showAllTask(tasks : LiveData<List<Task>>){}
    fun taskDoneSuccessfully(){}
    fun taskDeleteSuccessfully(){}
    fun taskEditedSuccessfully(){}
    fun showTask(task : Task){}
}