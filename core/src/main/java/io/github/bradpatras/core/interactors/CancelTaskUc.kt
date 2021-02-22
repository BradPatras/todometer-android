package io.github.bradpatras.core.interactors

import io.github.bradpatras.core.data.TaskRepository
import io.github.bradpatras.core.domain.Task
import javax.inject.Inject

class CancelTaskUc @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) = taskRepository.cancel(task)
}