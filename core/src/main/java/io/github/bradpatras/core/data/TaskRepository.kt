package io.github.bradpatras.core.data

import io.github.bradpatras.core.domain.Task
import io.github.bradpatras.core.domain.TaskState

class TaskRepository(private val dataSource: TaskDataSource) {

    suspend fun getAll(): List<Task> {
        return dataSource.getAll()
    }

    suspend fun add(task: Task) {
        return dataSource.add(task)
    }

    suspend fun update(task: Task) {
        return dataSource.update(task)
    }

    suspend fun cancel(task: Task) {
        return dataSource.cancel(task)
    }

    suspend fun removeCompletedTasks() {
        return dataSource.removeAllWithState(TaskState.COMPLETE)
    }
}