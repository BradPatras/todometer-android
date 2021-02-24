package io.github.bradpatras.todometer.utilities

import io.github.bradpatras.todometer.core.domain.Task
import io.github.bradpatras.todometer.core.domain.TaskState
import io.github.bradpatras.todometer.data.TaskEntity

fun TaskEntity.toTask(): Task {
    return Task(id, taskTitle, TaskState.fromInt(taskState))
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(id, title, state.rawValue)
}

fun List<TaskEntity>.toTaskList(): List<Task> {
    return map(TaskEntity::toTask)
}

fun List<Task>.toEntityList(): List<TaskEntity> {
    return map(Task::toEntity)
}
