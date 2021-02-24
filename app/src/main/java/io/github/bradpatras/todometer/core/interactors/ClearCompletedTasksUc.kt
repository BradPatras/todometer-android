package io.github.bradpatras.todometer.core.interactors

import io.github.bradpatras.todometer.core.data.TaskRepository
import javax.inject.Inject

class ClearCompletedTasksUc @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke() = taskRepository.removeCompletedTasks()
}