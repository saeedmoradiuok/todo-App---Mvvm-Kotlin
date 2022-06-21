package uok.sm.coding.todoapp.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import uok.sm.coding.todoapp.databinding.ItemTaskBinding
import uok.sm.coding.todoapp.model.Task
import uok.sm.coding.todoapp.utiles.convertMinToTime

class TaskAdapter(private val context: Context, private val tasks : List<Task>, private val listener : OnTaskClickedListener) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvTitle.text = tasks[position].title
        holder.binding.tvTime.text = convertMinToTime(tasks[position].time)
        if (tasks.size == 1){
            holder.binding.viewLine.isVisible = false
        }else if (tasks.size == 2 && position == 1){
            holder.binding.viewLine.isVisible = false
        }else holder.binding.viewLine.isVisible = position != tasks.size-1
        if (tasks[position].done){
            holder.binding.tvTitle.paintFlags = holder.binding.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.tvTitle.alpha = 0.4f
        }
        holder.binding.viewEmpty.isVisible = position == tasks.size-1
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class MyViewHolder(val binding : ItemTaskBinding) : RecyclerView.ViewHolder(binding.root),View.OnClickListener{
        init {
            binding.tvTitle.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onTaskClicked(tasks[adapterPosition].id,tasks[adapterPosition].done)
        }
    }

    interface OnTaskClickedListener{
        fun onTaskClicked(id : Int, done : Boolean)
    }
}