package io.github.bradpatras.todometer.data

import androidx.lifecycle.LiveData
import androidx.room.*

enum class TaskState(val rawValue: Int) {
    ACTIVE(1),
    LATER(2),
    COMPLETE(3)
}

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "task_title") var taskTitle: String?,
    @ColumnInfo(name = "task_state") var taskState: Int = TaskState.ACTIVE.rawValue
)

@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER by id ASC")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE task_state = :rawState")
    fun getAllWithState(rawState: Int): List<Task>

    @Update
    fun updateAll(tasks: List<Task>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tasks: List<Task>)

    @Delete
    fun delete(task: Task)
}
