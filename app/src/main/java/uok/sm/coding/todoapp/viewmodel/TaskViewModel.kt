package uok.sm.coding.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import saman.zamani.persiandate.PersianDate
import uok.sm.coding.todoapp.model.Task
import uok.sm.coding.todoapp.repository.TaskRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application)
    var taskCallback : TaskCallback? = null
    private var increaseDay : Long = 0

    fun getTodayDate(){
        var persianDate : PersianDate = PersianDate()
        var year = persianDate.shYear.toString()
        var month = persianDate.monthName().toString()
        var day = persianDate.shDay.toString()
        var dayName = persianDate.dayName().toString()
        taskCallback?.showDate(" $day $month $year",dayName,increaseDay)
    }

    fun getNextDate(){
        increaseDay += 1
        var persianDate : PersianDate = PersianDate().addDay(increaseDay)
        var year = persianDate.shYear.toString()
        var month = persianDate.monthName().toString()
        var day = persianDate.shDay.toString()
        var dayName = persianDate.dayName().toString()
        taskCallback?.showDate(" $day $month $year",dayName,increaseDay)
    }

    fun getPrevDate(){
        increaseDay -= 1
        var persianDate : PersianDate = PersianDate().addDay(increaseDay)
        var year = persianDate.shYear.toString()
        var month = persianDate.monthName().toString()
        var day = persianDate.shDay.toString()
        var dayName = persianDate.dayName().toString()
        taskCallback?.showDate(" $day $month $year",dayName,increaseDay)
    }

    fun saveTaskToDatabase(title : String, date : String, time : Long){
        when{
            title.isEmpty() -> taskCallback?.showError("کار خود را وارد کنید")
            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    taskRepository.addTask(Task(title, date, time))
                    withContext(Dispatchers.Main){
                        taskCallback?.taskSaved()
                    }
                }
            }
        }
    }

    fun getAllTask(date : String){
        taskCallback?.showAllTask(taskRepository.getAllTasks(date))
    }

    fun doneTask(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.doneTask(id)
            withContext(Dispatchers.Main){
                taskCallback?.taskDoneSuccessfully()
            }
        }
    }

    fun deleteTask(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(id)
            withContext(Dispatchers.Main){
                taskCallback?.taskDeleteSuccessfully()
            }
        }
    }

    fun editTask(id : Int,title : String,time : Long){
        when{
            title.isEmpty() -> taskCallback?.showError("کار را وارد کنید")
            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    taskRepository.editTask(id, title, time)
                    withContext(Dispatchers.Main){
                        taskCallback?.taskEditedSuccessfully()
                    }
                }
            }
        }
    }

    fun getTask(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            val task = taskRepository.getTask(id)
            withContext(Dispatchers.Main){
                taskCallback?.showTask(task)
            }
        }
    }
}