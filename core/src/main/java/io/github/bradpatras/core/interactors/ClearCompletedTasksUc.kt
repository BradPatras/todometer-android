package io.github.bradpatras.core.interactors

import io.github.bradpatras.core.data.TaskRepository
import javax.inject.Inject

class ClearCompletedTasksUc @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke() = taskRepository.removeCompletedTasks()
}