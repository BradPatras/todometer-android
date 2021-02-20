package io.github.bradpatras.todometer.presentation.tasklist

import androidx.lifecycle.*
import io.github.bradpatras.core.domain.Task
import io.github.bradpatras.core.domain.TaskState
import io.github.bradpatras.core.interactors.*
import io.github.bradpatras.todometer.TodoMeterApplication
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class TaskListViewModel : ViewModel() {

    init {
        TodoMeterApplication.instance.applicationComponent.inject(this)
    }

    @Inject
    lateinit var addTask: AddTaskUc

    @Inject
    lateinit var clearCompletedTasksUc: ClearCompletedTasksUc

    @Inject
    lateinit var getTasksUc: GetTasksUc

    @Inject
    lateinit var completeTaskUc: CompleteTaskUc

    @Inject
    lateinit var cancelTaskUc: CancelTaskUc

    @Inject
    lateinit var pauseTaskUc: PauseTaskUc

    @Inject
    lateinit var resumeTaskUc: ResumeTaskUc

    @Inject
    lateinit var resetTaskUc: ResetTaskUc

    var tasks: LiveData<List<Task>> = getTasksUc().asLiveData()

    var allTasksCompleted: MutableLiveData<Boolean> = MutableLiveData(false)

    suspend fun addTask(text: String) {
        addTask(Task(0, text, TaskState.ACTIVE))
    }

    suspend fun clearCompleted() {
        clearCompletedTasksUc()
    }

    suspend fun taskCompleted(task: Task) {
        getTasksUc().take(1).collect { tasks ->
            val allCompleted = tasks.doneTasks().isNotEmpty() && tasks.activeTasks().isEmpty()
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