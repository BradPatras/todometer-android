package io.github.bradpatras.todometer.utilities

import io.github.bradpatras.todometer.core.domain.Task
import io.github.bradpatras.todometer.core.domain.TaskState
import io.github.bradpatras.todometer.data.TaskEntity
import org.junit.Test
import org.junit.Assert.assertEquals

class TaskEntityMapperTest {
    @Test
    fun entityToDomainModel() {
        val id: Long = 0
        val title = "Test Task"
        val activeState = TaskState.ACTIVE
        val completeState = TaskState.COMPLETE
        val laterState = TaskState.LATER

        val activeEntityTask = TaskEntity(id = id, taskTitle = title, taskState = activeState.rawValue)
        val mappedActiveTask = activeEntityTask.toTask()
        assertEquals(activeEntityTask.id, mappedActiveTask.id)
        assertEquals(activeEntityTask.taskState, mappedActiveTask.state.rawValue)
        assertEquals(activeEntityTask.taskTitle, mappedActiveTask.title)

        val completeEntityTask = TaskEntity(id = id, taskTitle = title, taskState = completeState.rawValue)
        val mappedCompleteTask = completeEntityTask.toTask()
        assertEquals(completeEntityTask.id, mappedCompleteTask.id)
        assertEquals(completeEntityTask.taskState, mappedCompleteTask.state.rawValue)
        assertEquals(completeEntityTask.taskTitle, mappedCompleteTask.title)

        val laterEntityTask = TaskEntity(id = id, taskTitle = title, taskState = laterState.rawValue)
        val mappedLaterTask = laterEntityTask.toTask()
        assertEquals(laterEntityTask.id, mappedLaterTask.id)
        assertEquals(laterEntityTask.taskState, mappedLaterTask.state.rawValue)
        assertEquals(laterEntityTask.taskTitle, mappedLaterTask.title)
    }

    @Test
    fun domainModelToEntity() {
        val id: Long = 0
        val title = "Test Task"
        val activeState = TaskState.ACTIVE
        val completeState = TaskState.COMPLETE
        val laterState = TaskState.LATER

        val activeTask = Task(id = id, title = title, state = activeState)
        val mappedActiveEntityTask = activeTask.toEntity()
        assertEquals(mappedActiveEntityTask.id, activeTask.id)
        assertEquals(mappedActiveEntityTask.taskState, activeTask.state.rawValue)
        assertEquals(mappedActiveEntityTask.taskTitle, activeTask.title)

        val completeTask = Task(id = id, title = title, state = completeState)
        val mappedCompleteEntityTask = completeTask.toEntity()
        assertEquals(mappedCompleteEntityTask.id, completeTask.id)
        assertEquals(mappedCompleteEntityTask.taskState, completeTask.state.rawValue)
        assertEquals(mappedCompleteEntityTask.taskTitle, completeTask.title)

        val laterTask = Task(id = id, title = title, state = laterState)
        val mappedLaterEntityTask = laterTask.toEntity()
        assertEquals(mappedLaterEntityTask.id, laterTask.id)
        assertEquals(mappedLaterEntityTask.taskState, laterTask.state.rawValue)
        assertEquals(mappedLaterEntityTask.taskTitle, laterTask.title)
    }
}