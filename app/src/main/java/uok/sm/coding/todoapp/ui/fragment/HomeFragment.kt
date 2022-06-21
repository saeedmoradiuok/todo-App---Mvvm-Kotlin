package uok.sm.coding.todoapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uok.sm.coding.todoapp.R
import uok.sm.coding.todoapp.adapter.TaskAdapter
import uok.sm.coding.todoapp.databinding.FragmentHomeBinding
import uok.sm.coding.todoapp.model.Task
import uok.sm.coding.todoapp.viewmodel.TaskCallback
import uok.sm.coding.todoapp.viewmodel.TaskViewModel

class HomeFragment : Fragment(),TaskCallback,TaskAdapter.OnTaskClickedListener {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.green)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.taskCallback = this
        taskViewModel.getTodayDate()
        onViewClicked()
        return binding.root
    }

    private fun onViewClicked(){
        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddTaskFragment())
        }
        binding.imgNext.setOnClickListener { taskViewModel.getNextDate() }
        binding.imgPrev.setOnClickListener { taskViewModel.getPrevDate() }
    }

    override fun showDate(date: String, dayName: String, dayPosition: Long) {
        binding.tvDayName.text = dayName
        binding.tvDate.text = date
        taskViewModel.getAllTask(date)
    }

    override fun showAllTask(tasks: LiveData<List<Task>>) {
        tasks.observe(viewLifecycleOwner,Observer{
            if (it.isEmpty()){
                binding.rvTasks.isVisible = false
                binding.layoutEmptyState.root.isVisible = true
            }else{
                binding.rvTasks.isVisible = true
                binding.layoutEmptyState.root.isVisible = false
                binding.rvTasks.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = TaskAdapter(requireContext(),it,this@HomeFragment)
                }
            }
        })
    }

    override fun onTaskClicked(id: Int,done : Boolean) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToOperationsFragment(id,done))
    }
}