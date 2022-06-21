package uok.sm.coding.todoapp.repository


import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import uok.sm.coding.todoapp.db.TaskDatabase
import uok.sm.coding.todoapp.model.Task

class TaskRepository(private val application: Application) {

    suspend fun addTask(task : Task){
        TaskDatabase(application.applicationContext).getTaskDao().addTask(task)
    }

    fun getAllTasks(date : String) : LiveData<List<Task>>{
        return TaskDatabase(application.applicationContext).getTaskDao().getAllTask(date)
    }

    suspend fun deleteTask(id : Int){
        return TaskDatabase(application.applicationContext).getTaskDao().deleteTask(id)
    }

    suspend fun doneTask(id : Int){
        return TaskDatabase(application.applicationContext).getTaskDao().doneTask(id,true)
    }

    suspend fun editTask(id : Int,title : String,time : Long){
        TaskDatabase(application.applicationContext).getTaskDao().editTask(id, title, time)
    }

    suspend fun getTask(id: Int) : Task{
        return TaskDatabase(application.applicationContext).getTaskDao().getTask(id)
    }
}