package io.github.bradpatras.todometer

import io.github.bradpatras.todometer.core.data.TaskRepository
import io.github.bradpatras.todometer.core.domain.Task
import io.github.bradpatras.todometer.core.domain.TaskState
import io.github.bradpatras.todometer.core.interactors.*
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.Test

class UseCasesTest {

    private val repoMock = mockk<TaskRepository>(relaxed = true)
    private val activeTask = Task(0, "Test", TaskState.ACTIVE)
    private val pausedTask = activeTask.copy(state = TaskState.LATER)
    private val completedTask = activeTask.copy(state = TaskState.COMPLETE)

    @Test
    fun addTaskUcTest() {
        runBlocking { AddTaskUc(repoMock).invoke(activeTask) }

        coVerify { repoMock.add(activeTask) }
    }

    @Test
    fun cancelTaskUcTest() {
        runBlocking { CancelTaskUc(repoMock).invoke(activeTask) }

        coVerify { repoMock.cancel(activeTask) }
    }

    @Test
    fun clearCompletedTasksUcTest() {
        runBlocking { ClearCompletedTasksUc(repoMock).invoke() }

        coVerify { repoMock.removeCompletedTasks() }
    }

    @Test
    fun completeTaskUcTest() {
        runBlocking { CompleteTaskUc(repoMock).invoke(activeTask) }

        coVerify { repoMock.update(completedTask) }
    }

    @Test
    fun getTasksUcTest() {
        runBlocking { GetTasksUc(repoMock).invoke() }

        coVerify { repoMock.getAll() }
    }

    @Test
    fun pauseTaskUcTest() {
        runBlocking { PauseTaskUc(repoMock).invoke(activeTask) }

        coVerify { repoMock.update(pausedTask) }
    }

    @Test
    fun resetCompleteTaskUcTest() {
        runBlocking { ResetTaskUc(repoMock).invoke(completedTask) }

        coVerify { repoMock.update(activeTask) }
    }

    @Test
    fun resumeCompleteTaskUcTest() {
        runBlocking { ResetTaskUc(repoMock).invoke(pausedTask) }

        coVerify { repoMock.update(activeTask) }
    }
}