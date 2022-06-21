package uok.sm.coding.todoapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import uok.sm.coding.todoapp.R
import uok.sm.coding.todoapp.databinding.FragmentAddTaskBinding
import uok.sm.coding.todoapp.utiles.showMessage
import uok.sm.coding.todoapp.viewmodel.TaskCallback
import uok.sm.coding.todoapp.viewmodel.TaskViewModel

class AddTaskFragment : Fragment(),TaskCallback {

    private lateinit var binding : FragmentAddTaskBinding
    private lateinit var taskViewModel: TaskViewModel
    private var taskDate : String = ""
    private var taskTime : Long = 0
    private var taskTitle : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(layoutInflater)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.purple_500)
        binding.tpTime.setIs24HourView(true)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.taskCallback = this
        taskViewModel?.getTodayDate()
        onViewClicked()
        return binding.root
    }

    private fun onViewClicked(){
        binding.imgNext.setOnClickListener {
            taskViewModel.getNextDate()
        }
        binding.imgPrev.setOnClickListener {
            taskViewModel.getPrevDate()
        }
        binding.tvSaveTask.setOnClickListener {
             taskTitle = binding.etTitle.text.toString().trim()
             taskTime = (binding.tpTime.hour * 60 + binding.tpTime.minute).toLong()
             taskViewModel.saveTaskToDatabase(taskTitle,taskDate,taskTime)
        }
    }

    override fun showDate(date: String, dayName : String,dayPosition : Long) {
        binding.imgPrev.isVisible = dayPosition != 0L
        binding.tvDayName.text = dayName
        binding.tvDate.text = date
        taskDate = date
    }

    override fun showError(errorMessage: String) {
        requireContext().showMessage(errorMessage)
    }

    override fun taskSaved() {
        activity?.onBackPressed()
    }
    

}