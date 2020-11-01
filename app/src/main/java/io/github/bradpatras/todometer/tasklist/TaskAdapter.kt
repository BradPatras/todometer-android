package io.github.bradpatras.todometer.tasklist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.bradpatras.todometer.R
import io.github.bradpatras.todometer.data.Task
import io.github.bradpatras.todometer.data.TaskState
import kotlinx.android.synthetic.main.tasklist_item.view.*
import kotlinx.android.synthetic.main.tasklist_section_header.view.*

class TaskAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    var taskSection: TaskSection? = null
    private var laterSection: TaskSection? = null
    private var completedSection: TaskSection? = null

    private var isLaterSectionCollapsed: Boolean = false
    private var isCompletedSectionCollapsed: Boolean = false

    var itemActionHandler: ItemActionHandler? = null

    var tasks: List<Task> = emptyList()
        set(value) {
            field = value
            taskSection = TaskSection(
                id = 0L,
                tasks = value.filter { it.taskState == TaskState.ACTIVE.rawValue }
            )
            laterSection = TaskSection(
                id = 1L,
                sectionTitle = "Do Later",
                tasks = value.filter { it.taskState == TaskState.LATER.rawValue },
                isCollapsible = true,
                isCollapsed = isLaterSectionCollapsed
            )
            completedSection = TaskSection(
                id = 2L,
                sectionTitle = "Completed",
                tasks = value.filter { it.taskState == TaskState.COMPLETE.rawValue },
                isCollapsible = true,
                isCollapsed = isCompletedSectionCollapsed
            )
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return if (taskForPosition(position) != null)  R.layout.tasklist_item else R.layout.tasklist_section_header
    }

    override fun getItemId(position: Int): Long {
        // if task for position returns null, it means the position refers to a header item
        return taskForPosition(position)?.id ?: sectionForPosition(position)?.id ?: -1L
    }

    private fun taskForPosition(position: Int): Task? {
        val laterSectionHasTitle = laterSection?.hasTitle == true
        val laterSectionStartIndex = (taskSection?.itemCount ?: 0)
        val completedSectionHasTitle = completedSection?.hasTitle == true
        val completedSectionStartIndex = (taskSection?.itemCount ?: 0) + (laterSection?.itemCount ?: 0)

        return when (position) {
            in 0 until laterSectionStartIndex -> taskSection?.tasks?.get(position)
            in laterSectionStartIndex until completedSectionStartIndex -> {
                val isFirstLaterIndex = (position == laterSectionStartIndex)
                val isSectionHeaderIndex = (isFirstLaterIndex && laterSectionHasTitle)
                val sectionHeaderOffset = if (laterSection?.hasTitle == true) 1 else 0
                val laterItemPosition = position - (sectionHeaderOffset + laterSectionStartIndex)
                if (isSectionHeaderIndex) null else laterSection?.tasks?.get(laterItemPosition)
            }
            else -> {
                val isFirstCompletedIndex = position == completedSectionStartIndex
                val isSectionHeaderIndex = (isFirstCompletedIndex && completedSectionHasTitle)
                val sectionHeaderOffset = if (laterSection?.hasTitle == true) 1 else 0
                val completedItemPosition = position - (sectionHeaderOffset + completedSectionStartIndex)
                if (isSectionHeaderIndex) null else completedSection?.tasks?.get(completedItemPosition)
            }
        }
    }

    private fun sectionForPosition(position: Int): TaskSection? {
        val activeTaskCount = taskSection?.itemCount ?: 0
        val laterTaskCount = laterSection?.itemCount ?: 0
        return when (position) {
            in 0 until activeTaskCount -> taskSection
            in activeTaskCount until activeTaskCount + laterTaskCount -> laterSection
            else -> completedSection
        }
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
        return (taskSection?.itemCount ?: 0) +
                (laterSection?.itemCount ?: 0) +
                (completedSection?.itemCount ?: 0)
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

        holder.itemView.cancel_btn.setOnClickListener { itemActionHandler?.cancelPressed(this, task) }

        when (task.taskState) {
            TaskState.LATER.rawValue -> {
                holder.itemView.later_btn.setOnClickListener { itemActionHandler?.resumePressed(this, task) }
                holder.itemView.later_btn.setImageResource(R.drawable.ic_resume)
                holder.itemView.done_btn.setImageResource(R.drawable.ic_done)
                holder.itemView.done_btn.setOnClickListener { itemActionHandler?.donePressed(this, task) }
                holder.itemView.later_btn.visibility = View.VISIBLE
            }
            TaskState.ACTIVE.rawValue -> {
                holder.itemView.later_btn.setOnClickListener { itemActionHandler?.laterPressed(this, task) }
                holder.itemView.later_btn.setImageResource(R.drawable.ic_pause)
                holder.itemView.done_btn.setImageResource(R.drawable.ic_done)
                holder.itemView.done_btn.setOnClickListener { itemActionHandler?.donePressed(this, task) }
                holder.itemView.later_btn.visibility = View.VISIBLE
            }
            else -> {
                holder.itemView.done_btn.setOnClickListener { itemActionHandler?.resetPressed(this, task) }
                holder.itemView.done_btn.setImageResource(R.drawable.ic_reset)
                holder.itemView.later_btn.visibility = View.GONE
            }
        }
    }

    private fun onSectionPressed(section: TaskSection) {
        section.isCollapsed = !section.isCollapsed
        when (section) {
            completedSection -> {
                isCompletedSectionCollapsed = section.isCollapsed
            }
            laterSection -> {
                isLaterSectionCollapsed = section.isCollapsed
            }
        }

        notifyDataSetChanged()
    }

    private fun bindSectionHeaderViewHolder(holder: SectionHeaderViewHolder, position: Int) {
        val section = sectionForPosition(position) ?: return
        holder.itemView.header_tv.text = section.sectionTitle

        if (section.isCollapsible) {
            holder.itemView.collapse_iv.visibility = View.VISIBLE
            holder.itemView.collapse_iv.rotation = if (section.isCollapsed) 90f else 180f
            holder.itemView.setOnClickListener { onSectionPressed(section) }
        } else {
            holder.itemView.collapse_iv.visibility = View.GONE
            holder.itemView.setOnClickListener(null)
        }
    }

    interface ItemActionHandler {
        fun laterPressed(adapter: TaskAdapter, task: Task)
        fun resumePressed(adapter: TaskAdapter, task: Task)
        fun donePressed(adapter: TaskAdapter, task: Task)
        fun cancelPressed(adapter: TaskAdapter, task: Task)
        fun resetPressed(adapter: TaskAdapter, task: Task)
    }
}
