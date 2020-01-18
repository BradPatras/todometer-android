package io.github.bradpatras.todometer.data

import androidx.lifecycle.LiveData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    val allTasks: LiveData<List<Task>> = taskDao.getAll()

    fun doSomething() {
        val task = Task(0,"Do the thing", TaskState.ACTIVE.rawValue)
        val later = Task(0,"Also do this", TaskState.LATER.rawValue)
        taskDao.insertAll(listOf(task, later))
    }

    fun insertTask(task: Task): Single<Unit> {
        return Single.just(task)
            .observeOn(Schedulers.io())
            .map { taskDao.insertAll(listOf(it)) }
    }

    fun updateTask(task: Task): Single<Unit> {
        return Single.just(task)
            .observeOn(Schedulers.io())
            .map { taskDao.updateAll(listOf(it)) }
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

    fun cancelTask(task: Task): Single<Unit> {
        return Single.just(task)
            .observeOn(Schedulers.io())
            .map {
                taskDao.delete(it)
            }
    }
}