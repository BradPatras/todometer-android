package io.github.bradpatras.core.data

import io.github.bradpatras.core.domain.Task
import io.github.bradpatras.core.domain.TaskState

interface TaskDataSource {

    fun getAll(): List<Task>

    fun add(task: Task)

    fun update(task: Task)

    fun cancel(task: Task)

    fun removeAllWithState(state: TaskState)

}