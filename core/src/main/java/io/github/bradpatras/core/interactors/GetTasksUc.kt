package io.github.bradpatras.core.interactors

import io.github.bradpatras.core.data.TaskRepository
import javax.inject.Inject

class GetTasksUc @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke() = taskRepository.getAll()
}