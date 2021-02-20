package io.github.bradpatras.core.interactors

import io.github.bradpatras.core.data.TaskRepository
import io.github.bradpatras.core.domain.Task
import io.github.bradpatras.core.domain.TaskState
import javax.inject.Inject

class ResumeTaskUc @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        task.state = TaskState.ACTIVE
        taskRepository.update(task)
    }
}