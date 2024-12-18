package com.papb.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var inputTaskTitle: EditText
    private lateinit var inputTaskSubject: EditText
    private lateinit var addTaskButton: Button

    private val tasks = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter
    private var editingPosition: Int? = null  // Menyimpan posisi tugas yang sedang diedit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        inputTaskTitle = findViewById(R.id.inputTaskTitle)
        inputTaskSubject = findViewById(R.id.inputTaskSubject)
        addTaskButton = findViewById(R.id.addTaskButton)

        taskRecyclerView.layoutManager = LinearLayoutManager(this)

        // Adapter untuk RecyclerView
        taskAdapter = TaskAdapter(
            tasks,
            onEditTask = { position ->
                // Mengisi input dengan data tugas yang sedang diedit
                val task = tasks[position]
                inputTaskTitle.setText(task.title)
                inputTaskSubject.setText(task.subject)
                addTaskButton.setText("Save Task")
                editingPosition = position  // Menyimpan posisi tugas yang sedang diedit
            },
            onDeleteTask = { position ->
                // Menghapus tugas
                tasks.removeAt(position)
                taskAdapter.notifyItemRemoved(position)
                Toast.makeText(this, "Task removed", Toast.LENGTH_SHORT).show()
            },
            onDoneTask = { position ->
                // Menandai tugas sebagai selesai
                val task = tasks[position]
                task.isDone = true
                val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                task.timestamp = currentDate // Set timestamp selesai
                taskAdapter.notifyItemChanged(position) // Memperbarui tampilan item yang sudah selesai
                Toast.makeText(this, "Task marked as done", Toast.LENGTH_SHORT).show()
            }
        )

        taskRecyclerView.adapter = taskAdapter

        addTaskButton.setOnClickListener {
            val title = inputTaskTitle.text.toString()
            val subject = inputTaskSubject.text.toString()

            if (title.isNotEmpty() && subject.isNotEmpty()) {
                if (editingPosition != null) {
                    // Jika sedang mengedit, update tugas yang ada
                    val task = tasks[editingPosition!!]
                    task.title = title
                    task.subject = subject
                    task.timestamp = null  // Reset timestamp saat edit
                    task.isDone = false  // Reset status selesai
                    taskAdapter.notifyItemChanged(editingPosition!!) // Memperbarui item yang sedang diedit
                    Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show()
                } else {
                    // Jika tidak sedang mengedit, tambahkan tugas baru
                    val newTask = Task(title, subject)
                    tasks.add(newTask)
                    taskAdapter.notifyItemInserted(tasks.size - 1)
                    Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
                }

                // Setelah menyimpan atau menambah tugas, reset input dan tombol
                inputTaskTitle.text.clear()
                inputTaskSubject.text.clear()
                addTaskButton.setText("Add Task")
                editingPosition = null  // Reset posisi tugas yang sedang diedit
            } else {
                Toast.makeText(this, "Please enter both title and subject", Toast.LENGTH_SHORT).show()
            }
        }
    }
}