package com.papb.todolist

data class Task(
    var title: String,
    var subject: String,
    var isDone: Boolean = false,  // Menandakan apakah tugas sudah selesai
    var timestamp: String? = null  // Menyimpan waktu selesai
)