package io.github.bradpatras.todometer.feature.tasklist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.bradpatras.todometer.core.domain.Task
import io.github.bradpatras.todometer.core.domain.TaskState
import io.github.bradpatras.todometer.R
import io.github.bradpatras.todometer.databinding.TasklistItemBinding
import io.github.bradpatras.todometer.databinding.TasklistSectionHeaderBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class TaskAdapter(context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    var taskSection = TaskSectionFactory.main()
    private var laterSection = TaskSectionFactory.later()
    private var completedSection = TaskSectionFactory.completed()

    var collapsedSectionIds: Array<Long> = emptyArray()
        set(value) {
            field = value
            laterSection.isCollapsed = value.contains(laterSection.id)
            completedSection.isCollapsed = value.contains(completedSection.id)
            notifyDataSetChanged()
        }

    var itemActionHandler: ItemActionHandler? = null

    var tasks: List<Task> = emptyList()
        set(value) {
            field = value
            taskSection = taskSection.copy(tasks = value.filter { it.state == TaskState.ACTIVE })
            laterSection = laterSection.copy(
                tasks = value.filter { it.state == TaskState.LATER }
            )
            completedSection = completedSection.copy(
                tasks = value.filter { it.state == TaskState.COMPLETE }
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
        val laterSectionHasTitle = laterSection.hasTitle
        val laterSectionStartIndex = taskSection.itemCount
        val completedSectionHasTitle = completedSection.hasTitle
        val completedSectionStartIndex = taskSection.itemCount + laterSection.itemCount

        return when (position) {
            in 0 until laterSectionStartIndex -> taskSection.tasks[position]
            in laterSectionStartIndex until completedSectionStartIndex -> {
                val isFirstLaterIndex = (position == laterSectionStartIndex)
                val isSectionHeaderIndex = (isFirstLaterIndex && laterSectionHasTitle)
                val sectionHeaderOffset = if (laterSection.hasTitle) 1 else 0
                val laterItemPosition = position - (sectionHeaderOffset + laterSectionStartIndex)
                if (isSectionHeaderIndex) null else laterSection.tasks[laterItemPosition]
            }
            else -> {
                val isFirstCompletedIndex = position == completedSectionStartIndex
                val isSectionHeaderIndex = (isFirstCompletedIndex && completedSectionHasTitle)
                val sectionHeaderOffset = if (laterSection.hasTitle) 1 else 0
                val completedItemPosition = position - (sectionHeaderOffset + completedSectionStartIndex)
                if (isSectionHeaderIndex) null else completedSection.tasks[completedItemPosition]
            }
        }
    }

    private fun sectionForPosition(position: Int): TaskSection? {
        val activeTaskCount = taskSection.itemCount
        val laterTaskCount = laterSection.itemCount
        return when (position) {
            in 0 until activeTaskCount -> taskSection
            in activeTaskCount until activeTaskCount + laterTaskCount -> laterSection
            else -> completedSection
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.tasklist_item -> {
                val binding = TasklistItemBinding.inflate(layoutInflater, parent, false)
                TaskViewHolder(binding)
            }
            else -> {
                val binding = TasklistSectionHeaderBinding.inflate(layoutInflater, parent, false)
                SectionHeaderViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return taskSection.itemCount + laterSection.itemCount + completedSection.itemCount
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TaskViewHolder -> bindTaskViewHolder(holder, position)
            is SectionHeaderViewHolder -> bindSectionHeaderViewHolder(holder, position)
        }
    }

    private fun bindTaskViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskForPosition(position) ?: return
        holder.binding.taskTv.text = task.title

        holder.binding.cancelBtn.setOnClickListener { itemActionHandler?.cancelPressed(this, task) }

        when (task.state) {
            TaskState.LATER -> {
                holder.binding.laterBtn.setOnClickListener { itemActionHandler?.resumePressed(this, task) }
                holder.binding.laterBtn.setImageResource(R.drawable.ic_resume)
                holder.binding.doneBtn.setImageResource(R.drawable.ic_done)
                holder.binding.doneBtn.setOnClickListener { itemActionHandler?.donePressed(this, task) }
                holder.binding.laterBtn.visibility = View.VISIBLE
            }
            TaskState.ACTIVE -> {
                holder.binding.laterBtn.setOnClickListener { itemActionHandler?.laterPressed(this, task) }
                holder.binding.laterBtn.setImageResource(R.drawable.ic_pause)
                holder.binding.doneBtn.setImageResource(R.drawable.ic_done)
                holder.binding.doneBtn.setOnClickListener { itemActionHandler?.donePressed(this, task) }
                holder.binding.laterBtn.visibility = View.VISIBLE
            }
            else -> {
                holder.binding.doneBtn.setOnClickListener { itemActionHandler?.resetPressed(this, task) }
                holder.binding.doneBtn.setImageResource(R.drawable.ic_reset)
                holder.binding.laterBtn.visibility = View.GONE
            }
        }
    }

    private fun onSectionPressed(section: TaskSection) {
        itemActionHandler?.sectionHeaderPressed(this, section)
    }

    private fun bindSectionHeaderViewHolder(holder: SectionHeaderViewHolder, position: Int) {
        val section = sectionForPosition(position) ?: return
        holder.binding.headerTv.text = section.sectionTitle

        if (section.isCollapsible) {
            holder.binding.collapseIv.visibility = View.VISIBLE
            holder.binding.collapseIv.rotation = if (section.isCollapsed) 0f else 90f
            holder.itemView.setOnClickListener { onSectionPressed(section) }
        } else {
            holder.binding.collapseIv.visibility = View.GONE
            holder.itemView.setOnClickListener(null)
        }
    }

    interface ItemActionHandler {
        fun laterPressed(adapter: TaskAdapter, task: Task)
        fun resumePressed(adapter: TaskAdapter, task: Task)
        fun donePressed(adapter: TaskAdapter, task: Task)
        fun cancelPressed(adapter: TaskAdapter, task: Task)
        fun resetPressed(adapter: TaskAdapter, task: Task)
        fun sectionHeaderPressed(adapter: TaskAdapter, section: TaskSection)
    }
}
