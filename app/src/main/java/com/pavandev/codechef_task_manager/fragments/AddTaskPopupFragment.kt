package com.pavandev.codechef_task_manager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.animation.AnimatableView.Listener
import com.google.android.material.textfield.TextInputEditText
import com.pavandev.codechef_task_manager.R
import com.pavandev.codechef_task_manager.databinding.FragmentAddTaskPopupBinding
import com.pavandev.codechef_task_manager.utils.ToDoData

class AddTaskPopupFragment : DialogFragment() {

    private lateinit var binding: FragmentAddTaskPopupBinding
    private lateinit var listener: DialogNextBtnClickListeners
    private var toDoData: ToDoData? = null

    fun setListener(listener: DialogNextBtnClickListeners) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddTaskPopupFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = AddTaskPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )
            binding.todoEt.setText(toDoData?.task)
        }

        binding.nextBtnTask.setOnClickListener {
            val todoTask = binding.todoEt.text.toString().trim()
            if (todoTask.isNotEmpty()) {
                if (toDoData == null) {
                    listener.onSaveTask(todoTask, binding.todoEt)
                } else {
                    toDoData?.task = todoTask
                    listener.onUpdateTask(toDoData!!, binding.todoEt)
                }
            } else {
                Toast.makeText(context, "Please enter valid", Toast.LENGTH_SHORT).show()
            }
        }

        binding.closeTaskBtn.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListeners {
        fun onSaveTask(todo:String, todoEt:TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, todoEt:TextInputEditText)
    }
}