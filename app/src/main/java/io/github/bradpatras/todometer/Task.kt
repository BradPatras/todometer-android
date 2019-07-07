package io.github.bradpatras.todometer

import androidx.room.*

enum class TaskState(val rawValue: Int) {
    ACTIVE(1),
    LATER(2),
    COMPLETE(3)
}

@Entity
data class Task(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "task_title") var taskTitle: String?,
    @ColumnInfo(name = "task_state") var taskState: Int = TaskState.ACTIVE.rawValue
)

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE task_state = (:rawState)")
    fun getAllWithState(rawState: Int): List<Task>

    @Update
    fun updateAll(vararg task: Task)

    @Insert
    fun insertAll(vararg task: Task)

    @Delete
    fun delete(task: Task)
}
