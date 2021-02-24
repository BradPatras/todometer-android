package io.github.bradpatras.todometer.utilities

import io.github.bradpatras.todometer.core.domain.Task
import io.github.bradpatras.todometer.core.domain.TaskState

fun List<Task>.doneTasks(): List<Task> = this.filter { it.state == TaskState.COMPLETE }

fun List<Task>.laterTasks(): List<Task> = this.filter { it.state == TaskState.LATER }

fun List<Task>.activeTasks(): List<Task> = this.filter { it.state == TaskState.ACTIVE }