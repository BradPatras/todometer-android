package io.github.bradpatras.todometer

import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    fun doSomething() {
        val task = Task(0, "first", TaskState.ACTIVE.rawValue)
        taskDao.insertAll(listOf(task))
    }
}