package io.github.bradpatras.todometer.core.domain

data class Task(val id: Long, val title: String?, var state: TaskState)
