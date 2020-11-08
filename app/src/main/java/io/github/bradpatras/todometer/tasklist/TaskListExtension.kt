package io.github.bradpatras.todometer.tasklist

import io.github.bradpatras.todometer.data.Task
import io.github.bradpatras.todometer.data.TaskState

fun List<Task>.doneTasks(): List<Task> = this.filter { it.taskState == TaskState.COMPLETE.rawValue }

fun List<Task>.laterTasks(): List<Task> = this.filter { it.taskState == TaskState.LATER.rawValue }

fun List<Task>.activeTasks(): List<Task> = this.filter { it.taskState == TaskState.ACTIVE.rawValue }