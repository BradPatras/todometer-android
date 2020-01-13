package io.github.bradpatras.todometer.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    fun doSomething() {
        val task = Task(0, "Do the thing", TaskState.ACTIVE.rawValue)
        val later = Task(1, "Also do this", TaskState.LATER.rawValue)
        taskDao.insertAll(listOf(task, later))
    }

    fun getActiveTasks(): Single<List<Task>> {
        return Single.just(Unit)
            .observeOn(Schedulers.io())
            .map {
                taskDao.getAllWithState(TaskState.ACTIVE.rawValue)
            }
    }

    fun getLaterTasks(): Single<List<Task>> {
        return Single.just(Unit)
            .observeOn(Schedulers.io())
            .map {
                taskDao.getAllWithState(TaskState.LATER.rawValue)
            }
    }
}