package uok.sm.coding.todoapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import uok.sm.coding.todoapp.R
import uok.sm.coding.todoapp.databinding.FragmentEditBinding
import uok.sm.coding.todoapp.model.Task
import uok.sm.coding.todoapp.utiles.showMessage
import uok.sm.coding.todoapp.viewmodel.TaskCallback
import uok.sm.coding.todoapp.viewmodel.TaskViewModel

class EditFragment : Fragment(),TaskCallback {

    private lateinit var binding : FragmentEditBinding
    private val args : EditFragmentArgs by navArgs()
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(layoutInflater)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.purple_500)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.taskCallback = this
        binding.tpTime.setIs24HourView(true)
        taskViewModel.getTask(args.id)
        onViewClicked()
        return binding.root
    }

    override fun showTask(task: Task) {
        binding.etTitle.setText(task.title)
        val hour = task.time / 60
        val minutes = task.time % 60
        binding.tpTime.hour = hour.toInt()
        binding.tpTime.minute = minutes.toInt()
    }

    private fun onViewClicked(){
        binding.tvSaveTask.setOnClickListener {
            val title : String = binding.etTitle.text.toString().trim()
            var time : Long = (binding.tpTime.hour * 60 + binding.tpTime.minute).toLong()
            taskViewModel.editTask(args.id,title, time)
        }
    }

    override fun showError(errorMessage: String) {
        requireContext().showMessage(errorMessage)
    }

    override fun taskEditedSuccessfully() {
        activity?.onBackPressed()
    }
}