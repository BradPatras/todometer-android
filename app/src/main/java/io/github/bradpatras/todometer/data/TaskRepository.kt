package io.github.bradpatras.todometer.data

import androidx.lifecycle.LiveData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    val allTasks: LiveData<List<Task>> = taskDao.getAll()

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

    fun clearDoneTasks(): Single<Unit> {
        return Single.just(Unit)
            .observeOn(Schedulers.io())
            .map { taskDao.deleteAllWithState(TaskState.COMPLETE.rawValue) }
    }

    fun cancelTask(task: Task): Single<Unit> {
        return Single.just(task)
            .observeOn(Schedulers.io())
            .map { taskDao.delete(it) }
    }
}