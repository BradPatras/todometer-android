package io.github.bradpatras.todometer.tasklist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.bradpatras.todometer.R
import io.github.bradpatras.todometer.data.Task
import io.github.bradpatras.todometer.data.TaskState
import kotlinx.android.synthetic.main.tasklist_item.view.*

class TaskAdapter(context: Context): RecyclerView.Adapter<TaskViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    var tasks: List<Task> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = layoutInflater.inflate(R.layout.tasklist_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.count()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks.getOrNull(position) ?: return
        holder.itemView.task_tv.text = task.taskTitle
        val laterResource = if (task.taskState == TaskState.LATER.rawValue) R.drawable.ic_resume else R.drawable.ic_pause
        holder.itemView.later_btn.setImageResource(laterResource)
    }
}
