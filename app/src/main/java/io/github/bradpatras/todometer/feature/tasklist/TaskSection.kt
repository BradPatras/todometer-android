package io.github.bradpatras.todometer.feature.tasklist

import io.github.bradpatras.todometer.core.domain.Task

data class TaskSection(
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
class TaskSectionFactory {
    companion object {
        fun main() = TaskSection(id = 0L, tasks = emptyList(), isCollapsible = false)

        fun later() = TaskSection(
            id = 1L,
            sectionTitle = "Do Later",
            tasks = emptyList(),
            isCollapsible = true
        )

        fun completed() = TaskSection(
            id = 2L,
            sectionTitle = "Completed",
            tasks = emptyList(),
            isCollapsible = true,
            isCollapsed = true
        )
    }
}

