package io.github.bradpatras.core.interactors

import io.github.bradpatras.core.data.TaskRepository

class GetTasks(private val taskRepository: TaskRepository) {
    suspend operator fun invoke() = taskRepository.getAll()
}