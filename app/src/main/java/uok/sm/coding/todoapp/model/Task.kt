package uok.sm.coding.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    var title : String,
    var date : String,
    var time : Long,
    var done : Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}