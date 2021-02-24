package io.github.bradpatras.todometer.core.interactors

import io.github.bradpatras.todometer.core.data.TaskRepository
import io.github.bradpatras.todometer.core.domain.Task
import javax.inject.Inject

class AddTaskUc @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) = taskRepository.add(task)
}