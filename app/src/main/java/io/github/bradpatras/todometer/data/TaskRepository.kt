package io.github.bradpatras.todometer.data

import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    init {
        print("initializing")
    }
    fun doSomething() {
        val task =
            Task(
                0,
                "first",
                TaskState.ACTIVE.rawValue
            )
        taskDao.insertAll(listOf(task))
    }
}