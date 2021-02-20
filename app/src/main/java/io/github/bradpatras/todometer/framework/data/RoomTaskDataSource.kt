package io.github.bradpatras.todometer.framework.data

import io.github.bradpatras.core.data.TaskDataSource
import io.github.bradpatras.core.domain.Task
import io.github.bradpatras.core.domain.TaskState
import io.github.bradpatras.todometer.framework.toEntity
import io.github.bradpatras.todometer.framework.toTaskList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomTaskDataSource @Inject constructor(private val taskDao: TaskDao) : TaskDataSource {

    override fun getAll(): Flow<List<Task>> {
        return taskDao.getAll().map(List<TaskEntity>::toTaskList)
    }

    override suspend fun add(task: Task) {
        taskDao.insertAll(listOf(task.toEntity()))
    }

    override suspend fun update(task: Task) {
        taskDao.updateAll(listOf(task.toEntity()))
    }

    override suspend fun cancel(task: Task) {
        taskDao.delete(task.toEntity())
    }

    override suspend fun removeAllWithState(state: TaskState) {
        taskDao.deleteAllWithState(state.rawValue)
    }
}