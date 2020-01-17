package io.github.bradpatras.todometer.tasklist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.github.bradpatras.todometer.R
import io.github.bradpatras.todometer.data.Task
import io.github.bradpatras.todometer.data.TaskState
import kotlinx.android.synthetic.main.tasklist_item.view.*
import kotlinx.android.synthetic.main.tasklist_section_header.view.*

class TaskAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var taskSection: TaskSection? = null
    private var laterSection: TaskSection? = null

    var itemActionHandler: TaskAdapter.ItemActionHandler? = null

    var activeTasks: List<Task> = emptyList()
        set(value) {
            field = value
            taskSection = TaskSection(tasks = value)
        }

    var laterTasks: List<Task> = emptyList()
        set(value) {
            field = value
            laterSection = TaskSection("Do Later", value)
        }


    override fun getItemViewType(position: Int): Int {
        val taskCount = taskSection?.itemCount ?: 0

        return when(position) {
            in 0 until (taskSection?.itemCount ?: 0) -> R.layout.tasklist_item
            in (taskSection?.itemCount ?: 0) until getItemCount() -> {
                val isFirstLaterIndex = (position == taskCount)
                if (isFirstLaterIndex && laterSection?.hasTitle == true) R.layout.tasklist_section_header else R.layout.tasklist_item
            }
            else -> R.layout.tasklist_item
        }
    }

    fun taskForPosition(position: Int): Task? {
        val taskCount = taskSection?.itemCount ?: 0

        return when (position) {
            in 0 until (taskSection?.itemCount ?: 0) -> taskSection?.tasks?.get(position)
            in (taskSection?.itemCount ?: 0)..itemCount -> {
                val isFirstLaterIndex = (position == taskCount)
                val isSectionHeaderIndex = (isFirstLaterIndex && laterSection?.hasTitle == true)
                val sectionHeaderOffset = if (laterSection?.hasTitle == true) 1 else 0
                val laterItemPosition = position - (sectionHeaderOffset + taskCount)
                if (isSectionHeaderIndex) null else laterSection?.tasks?.get(laterItemPosition)
            }
            else -> null
        }
    }

    private fun sectionForPosition(position: Int): TaskSection? {
        val activeTaskCount = taskSection?.itemCount ?: 0
        return if (position in 0 until activeTaskCount) taskSection else laterSection
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.tasklist_item -> {
                val view = layoutInflater.inflate(R.layout.tasklist_item, parent, false)
                TaskViewHolder(view)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.tasklist_section_header, parent, false)
                SectionHeaderViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return (taskSection?.itemCount ?: 0) + (laterSection?.itemCount ?: 0)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TaskViewHolder -> bindTaskViewHolder(holder, position)
            is SectionHeaderViewHolder -> bindSectionHeaderViewHolder(holder, position)
        }
    }

    private fun bindTaskViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskForPosition(position) ?: return
        holder.itemView.task_tv.text = task.taskTitle

        holder.itemView.done_btn.setOnClickListener { itemActionHandler?.donePressed(this, task) }
        holder.itemView.cancel_btn.setOnClickListener { itemActionHandler?.cancelPressed(this, task) }
        if (task.taskState == TaskState.LATER.rawValue) {
            holder.itemView.later_btn.setOnClickListener { itemActionHandler?.resumePressed(this, task) }
            holder.itemView.later_btn.setImageResource(R.drawable.ic_resume)
        } else {
            holder.itemView.later_btn.setOnClickListener { itemActionHandler?.laterPressed(this, task) }
            holder.itemView.later_btn.setImageResource(R.drawable.ic_pause)
        }
    }

    private fun bindSectionHeaderViewHolder(holder: SectionHeaderViewHolder, position: Int) {
        val section = sectionForPosition(position) ?: return
        holder.itemView.header_tv.text = section.sectionTitle
    }

    interface ItemActionHandler {
        fun laterPressed(adapter: TaskAdapter, task: Task)
        fun resumePressed(adapter: TaskAdapter, task: Task)
        fun donePressed(adapter: TaskAdapter, task: Task)
        fun cancelPressed(adapter: TaskAdapter, task: Task)
    }
}
