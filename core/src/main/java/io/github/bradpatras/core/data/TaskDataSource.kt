package io.github.bradpatras.core.data

import io.github.bradpatras.core.domain.Task
import io.github.bradpatras.core.domain.TaskState
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {

    fun getAll(): Flow<List<Task>>

    suspend fun add(task: Task)

    suspend fun update(task: Task)

    suspend fun cancel(task: Task)

    suspend fun removeAllWithState(state: TaskState)

}