package io.github.bradpatras.todometer.feature.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.bradpatras.todometer.core.domain.Task
import io.github.bradpatras.todometer.R
import io.github.bradpatras.todometer.databinding.ActivityMainBinding
import io.github.bradpatras.todometer.feature.about.AboutActivity
import io.github.bradpatras.todometer.utilities.activeTasks
import io.github.bradpatras.todometer.utilities.doneTasks
import io.github.bradpatras.todometer.utilities.laterTasks
import io.github.bradpatras.todometer.utilities.showKeyboard
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskListActivity : AppCompatActivity(), TaskAdapter.ItemActionHandler {
    private lateinit var binding: ActivityMainBinding
    private var taskListAdapter: TaskAdapter? = null
    private val contentView get() = binding.appBarMain.contentMain
    private val viewModel: TaskListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val taskAdapter = this.taskListAdapter ?: TaskAdapter(this)
        taskAdapter.itemActionHandler = this
        taskAdapter.setHasStableIds(true)
        binding.appBarMain.contentMain.recyclerView.layoutManager = LinearLayoutManager(this)
        contentView.recyclerView.adapter = taskAdapter
        taskListAdapter = taskAdapter

        contentView.addBtn.setOnClickListener {
            if (contentView.addEt.text.isNotBlank()) {
                submitTask()
            } else {
                contentView.addEt.requestFocus()
                contentView.addEt.showKeyboard()
            }
        }

        viewModel.tasks.observe(this) { tasks: List<Task> ->
            val laterTasks = tasks.laterTasks()
            val activeTasks = tasks.activeTasks()
            val doneTasks = tasks.doneTasks()
            taskListAdapter?.tasks = activeTasks + laterTasks + doneTasks
            updateTodoMeter(activeTasks.count(), laterTasks.count(), doneTasks.count())
        }

        viewModel.allTasksCompleted.observe(this) { allCompleted ->
            if (allCompleted) showDoneAnimation()
        }
    }

    private fun submitTask() {
        val taskText = contentView.addEt.text.toString()
        lifecycleScope.launch {
            viewModel.addTask(taskText)
        }

        contentView.addEt.text.clear()
        taskListAdapter?.taskSection?.itemCount?.let { contentView.recyclerView.scrollToPosition(it - 1) }
    }

    private fun updateTodoMeter(active: Int, later: Int, done: Int) {
        val taskCount = active + later + done
        contentView.todoMeter.doneMeterProgress = done.toFloat() / taskCount.toFloat()
        contentView.todoMeter.laterMeterProgress = later.toFloat() / taskCount.toFloat()
    }

    private fun clearCompleted() {
        lifecycleScope.launch {
            viewModel.clearCompleted()
        }
    }

    private fun showAbout() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun showDoneAnimation() {
        contentView.doneAnimationView.apply {
            alpha = 1f
            addAnimatorUpdateListener { animator ->
                if (animator.animatedFraction >= 1f) {
                    animate().alpha(0f).withEndAction {
                        progress = 0f
                    }.start()
                }
            }
            playAnimation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_completed -> {
                clearCompleted()
                return true
            }
            R.id.action_about -> {
                showAbout()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun laterPressed(adapter: TaskAdapter, task: Task) {
        lifecycleScope.launch {
            viewModel.taskPaused(task)
        }
    }

    override fun resumePressed(adapter: TaskAdapter, task: Task) {
        lifecycleScope.launch {
            viewModel.taskResumed(task)
        }
    }

    override fun donePressed(adapter: TaskAdapter, task: Task) {
        lifecycleScope.launch {
            viewModel.taskCompleted(task)
        }
    }

    override fun cancelPressed(adapter: TaskAdapter, task: Task) {
        lifecycleScope.launch {
            viewModel.taskCancelled(task)
        }
    }

    override fun resetPressed(adapter: TaskAdapter, task: Task) {
        lifecycleScope.launch {
            viewModel.taskReset(task)
        }
    }
}
