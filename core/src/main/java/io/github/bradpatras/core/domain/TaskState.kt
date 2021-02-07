package io.github.bradpatras.core.domain

enum class TaskState(val rawValue: Int) {
    ACTIVE(1),
    LATER(2),
    COMPLETE(3)
}