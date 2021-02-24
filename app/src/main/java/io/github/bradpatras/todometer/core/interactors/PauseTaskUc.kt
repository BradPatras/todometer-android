package io.github.bradpatras.todometer.core.interactors

import io.github.bradpatras.todometer.core.data.TaskRepository
import io.github.bradpatras.todometer.core.domain.Task
import io.github.bradpatras.todometer.core.domain.TaskState
import javax.inject.Inject

class PauseTaskUc @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        task.state = TaskState.LATER
        taskRepository.update(task)
    }
}