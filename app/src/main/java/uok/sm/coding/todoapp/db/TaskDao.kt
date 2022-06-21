package uok.sm.coding.todoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uok.sm.coding.todoapp.model.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM tasks WHERE date =:date ORDER BY time")
    fun getAllTask(date : String) : LiveData<List<Task>>

    @Query("DELETE FROM tasks WHERE id =:id")
    suspend fun deleteTask(id : Int)

    @Query("UPDATE tasks SET done=:done WHERE id =:id ")
    suspend fun doneTask(id : Int,done : Boolean)

    @Query("UPDATE tasks SET title=:title , time=:time WHERE id =:id ")
    suspend fun editTask(id : Int,title : String,time : Long)

    @Query("SELECT * FROM tasks WHERE id=:id")
    suspend fun getTask(id: Int) : Task
}