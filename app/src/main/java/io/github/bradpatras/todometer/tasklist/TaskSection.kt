package io.github.bradpatras.todometer.tasklist

import io.github.bradpatras.todometer.data.Task

class TaskSection(val sectionTitle: String? = null, val tasks: List<Task>) {
    val itemCount = tasks.count() + if (sectionTitle == null || tasks.isEmpty()) 0 else 1
    val hasTitle = sectionTitle != null
}