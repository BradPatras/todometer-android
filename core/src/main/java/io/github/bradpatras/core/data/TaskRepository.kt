package io.github.bradpatras.core.data

import io.github.bradpatras.core.domain.Task
import io.github.bradpatras.core.domain.TaskState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository(private val dataSource: TaskDataSource) {

    fun getAll(): Flow<List<Task>> {
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