package uok.sm.coding.todoapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uok.sm.coding.todoapp.databinding.FragmentHomeBinding
import uok.sm.coding.todoapp.databinding.FragmentOperationsBinding
import uok.sm.coding.todoapp.viewmodel.TaskCallback
import uok.sm.coding.todoapp.viewmodel.TaskViewModel

class OperationsFragment : BottomSheetDialogFragment(), TaskCallback {

    private lateinit var binding: FragmentOperationsBinding
    private val args: OperationsFragmentArgs by navArgs()
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOperationsBinding.inflate(layoutInflater)
        updateUi()
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.taskCallback = this
        onViewClicked()
        return binding.root
    }

    private fun updateUi() {
        if (args.done) {
            binding.layoutDone.isVisible = false
            binding.layoutEdit.isVisible = false
            binding.viewLine1.isVisible = false
            binding.viewLine2.isVisible = false
        }
    }

    private fun onViewClicked() {
        binding.imgClose.setOnClickListener { activity?.onBackPressed() }
        binding.layoutDelete.setOnClickListener { taskViewModel.deleteTask(args.id) }
        binding.layoutDone.setOnClickListener { taskViewModel.doneTask(args.id) }
        binding.layoutEdit.setOnClickListener {
            findNavController().navigate(OperationsFragmentDirections.actionOperationsFragmentToEditFragment(args.id))
        }
    }

    override fun taskDeleteSuccessfully() {
        activity?.onBackPressed()
    }

    override fun taskDoneSuccessfully() {
        activity?.onBackPressed()
    }
}