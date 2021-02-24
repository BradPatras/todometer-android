package io.github.bradpatras.todometer.data

import androidx.room.*
import io.github.bradpatras.todometer.core.domain.TaskState
import kotlinx.coroutines.flow.Flow

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "task_title") var taskTitle: String?,
    @ColumnInfo(name = "task_state") var taskState: Int = TaskState.ACTIVE.rawValue
)

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskEntity ORDER by id ASC")
    fun getAll(): Flow<List<TaskEntity>>

    @Update
    fun updateAll(tasks: List<TaskEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tasks: List<TaskEntity>)

    @Delete
    fun delete(task: TaskEntity)

    @Query("DELETE FROM TaskEntity WHERE task_state = :rawState")
    fun deleteAllWithState(rawState: Int): Unit
}
