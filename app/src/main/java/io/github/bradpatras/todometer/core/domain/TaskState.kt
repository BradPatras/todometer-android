package io.github.bradpatras.todometer.core.domain

enum class TaskState(val rawValue: Int) {
    ACTIVE(1),
    LATER(2),
    COMPLETE(3);

    companion object {
        fun fromInt(rawValue: Int): TaskState = values().first { rawValue == it.rawValue }
    }
}