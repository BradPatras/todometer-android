package io.github.bradpatras.todometer.feature.tasklist

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bradpatras.todometer.core.domain.Task
import io.github.bradpatras.todometer.core.domain.TaskState
import io.github.bradpatras.todometer.core.interactors.AddTaskUc
import io.github.bradpatras.todometer.core.interactors.CancelTaskUc
import io.github.bradpatras.todometer.core.interactors.ClearCompletedTasksUc
import io.github.bradpatras.todometer.core.interactors.CompleteTaskUc
import io.github.bradpatras.todometer.core.interactors.GetTasksUc
import io.github.bradpatras.todometer.core.interactors.PauseTaskUc
import io.github.bradpatras.todometer.core.interactors.ResetTaskUc
import io.github.bradpatras.todometer.core.interactors.ResumeTaskUc
import io.github.bradpatras.todometer.utilities.activeTasks
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    val addTaskUc: AddTaskUc,
    val clearCompletedTasksUc: ClearCompletedTasksUc,
    val getTasksUc: GetTasksUc,
    val completeTaskUc: CompleteTaskUc,
    val cancelTaskUc: CancelTaskUc,
    val pauseTaskUc: PauseTaskUc,
    val resumeTaskUc: ResumeTaskUc,
    val resetTaskUc: ResetTaskUc
) : ViewModel() {

    val tasks: LiveData<List<Task>> = getTasksUc().asLiveData()

    val allTasksCompleted: MutableLiveData<Boolean> = MutableLiveData(false)

    suspend fun addTask(text: String) {
        addTaskUc(Task(0, text, TaskState.ACTIVE))
    }

    suspend fun clearCompleted() {
        clearCompletedTasksUc()
    }

    suspend fun taskCompleted(task: Task) {
        getTasksUc().take(1).collect { tasks ->
            if (tasks.activeTasks().count() != 1) return@collect
            val lastTask = tasks.activeTasks().lastOrNull() ?: return@collect
            val allCompleted = lastTask.id == task.id
            allTasksCompleted.postValue(allCompleted)
        }

        completeTaskUc(task)
    }

    suspend fun taskCancelled(task: Task) {
        cancelTaskUc(task)
    }

    suspend fun taskPaused(task: Task) {
        pauseTaskUc(task)
    }

    suspend fun taskResumed(task: Task) {
        resumeTaskUc(task)
    }

    suspend fun taskReset(task: Task) {
        resetTaskUc(task)
    }
}