package uok.sm.coding.todoapp.utiles

import android.content.Context
import android.widget.Toast

fun Context.showMessage(message : String){
    Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
}

fun convertMinToTime(time : Long) : String{
    var hour = time / 60
    var minutes = time % 60
    return String.format("%d:%02d",hour,minutes)
}






