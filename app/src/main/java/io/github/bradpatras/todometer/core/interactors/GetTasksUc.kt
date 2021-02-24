package io.github.bradpatras.todometer.core.interactors

import io.github.bradpatras.todometer.core.data.TaskRepository
import javax.inject.Inject

class GetTasksUc @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke() = taskRepository.getAll()
}