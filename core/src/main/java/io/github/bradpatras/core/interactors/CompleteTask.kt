package io.github.bradpatras.core.interactors

import io.github.bradpatras.core.data.TaskRepository
import io.github.bradpatras.core.domain.Task
import io.github.bradpatras.core.domain.TaskState

class CompleteTask(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        task.state = TaskState.COMPLETE
        taskRepository.update(task)
    }
}