package io.github.bradpatras.todometer.presentation.tasklist

import io.github.bradpatras.core.domain.Task
import io.github.bradpatras.core.domain.TaskState

fun List<Task>.doneTasks(): List<Task> = this.filter { it.state == TaskState.COMPLETE }

fun List<Task>.laterTasks(): List<Task> = this.filter { it.state == TaskState.LATER }

fun List<Task>.activeTasks(): List<Task> = this.filter { it.state == TaskState.ACTIVE }