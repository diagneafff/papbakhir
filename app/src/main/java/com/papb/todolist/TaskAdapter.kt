package com.papb.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onEditTask: (Int) -> Unit,
    private val onDeleteTask: (Int) -> Unit,
    private val onDoneTask: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskSubject: TextView = itemView.findViewById(R.id.taskSubject)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val doneButton: Button = itemView.findViewById(R.id.doneButton)
        val timestampText: TextView = itemView.findViewById(R.id.timestampText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.taskTitle.text = task.title
        holder.taskSubject.text = task.subject

        if (task.isDone) {
            holder.doneButton.visibility = View.GONE // Sembunyikan tombol "Done"
            holder.editButton.visibility = View.GONE // Sembunyikan tombol "Edit"
            holder.deleteButton.visibility = View.GONE // Sembunyikan tombol "Delete"
            holder.timestampText.visibility = View.VISIBLE // Tampilkan timestamp
            holder.timestampText.text = "Completed at: ${task.timestamp}"
        } else {
            holder.doneButton.visibility = View.VISIBLE // Tampilkan tombol "Done"
            holder.editButton.visibility = View.VISIBLE // Tampilkan tombol "Edit"
            holder.deleteButton.visibility = View.VISIBLE // Tampilkan tombol "Delete"
            holder.timestampText.visibility = View.GONE // Sembunyikan timestamp
        }

        holder.editButton.setOnClickListener { onEditTask(position) }
        holder.deleteButton.setOnClickListener { onDeleteTask(position) }
        holder.doneButton.setOnClickListener { onDoneTask(position) }
    }

    override fun getItemCount(): Int = tasks.size
}