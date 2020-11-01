package io.github.bradpatras.todometer.tasklist

import io.github.bradpatras.todometer.data.Task

class TaskSection(
    val id: Long,
    val sectionTitle: String? = null,
    val tasks: List<Task>,
    val isCollapsible: Boolean = false,
    var isCollapsed: Boolean = false
) {
    val itemCount: Int
        get() {
            val tasksCount = if (isCollapsed) 0 else tasks.count()
            val headerCount = if (sectionTitle == null || tasks.isEmpty()) 0 else 1
            return tasksCount + headerCount
        }

    val hasTitle = sectionTitle != null
}