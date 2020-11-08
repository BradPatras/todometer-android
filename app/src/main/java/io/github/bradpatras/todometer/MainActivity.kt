package io.github.bradpatras.todometer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.bradpatras.todometer.utilities.onDone
import io.github.bradpatras.todometer.data.Task
import io.github.bradpatras.todometer.data.TaskRepository
import io.github.bradpatras.todometer.data.TaskState
import io.github.bradpatras.todometer.tasklist.TaskAdapter
import io.github.bradpatras.todometer.tasklist.activeTasks
import io.github.bradpatras.todometer.tasklist.doneTasks
import io.github.bradpatras.todometer.tasklist.laterTasks
import io.github.bradpatras.todometer.utilities.hideKeyboard
import io.github.bradpatras.todometer.utilities.observeOnce
import io.github.bradpatras.todometer.utilities.showKeyboard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), TaskAdapter.ItemActionHandler {

    private var taskListAdapter: TaskAdapter? = null
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    @Inject lateinit var taskRepository: TaskRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        TodoMeterApplication.instance.applicationComponent.inject(this)

        val taskAdapter = this.taskListAdapter ?: TaskAdapter(this)
        taskAdapter.itemActionHandler = this
        taskAdapter.setHasStableIds(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter
        taskListAdapter = taskAdapter

        add_btn.setOnClickListener {
            if (add_et.text.isNotBlank()) {
                submitTask()
                add_et.hideKeyboard()
                add_et.clearFocus()
            } else {
                add_et.requestFocus()
                add_et.showKeyboard()
            }
        }

        add_et.onDone {
            if (add_et.text.isNotBlank()) {
                submitTask()
            }
        }

        taskRepository.allTasks.observe(this, { tasks: List<Task> ->
            val laterTasks = tasks.laterTasks()
            val activeTasks = tasks.activeTasks()
            val doneTasks = tasks.doneTasks()
            taskListAdapter?.tasks = activeTasks + laterTasks + doneTasks
            updateTodoMeter(activeTasks, laterTasks, doneTasks)
        })
    }

    private fun submitTask() {
        taskRepository.insertTask(Task(0, add_et.text.toString(), TaskState.ACTIVE.rawValue)).subscribe()
        add_et.text.clear()
        taskListAdapter?.taskSection?.itemCount?.let { recyclerView.scrollToPosition(it - 1) }
    }

    private fun updateTodoMeter(active: List<Task>, later: List<Task>, done: List<Task>) {
        val taskCount = active.count() + later.count() + done.count()
        todo_meter.doneMeterProgress = done.count().toFloat() / taskCount.toFloat()
        todo_meter.laterMeterProgress = later.count().toFloat() / taskCount.toFloat()
    }

    private fun clearCompleted() {
        taskRepository.clearDoneTasks().subscribe()
    }

    private fun showAbout() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun showDoneAnimationIfNeeded() {
        taskRepository.allTasks.observeOnce(this, { tasks ->
            if (tasks.doneTasks().isNotEmpty() && tasks.activeTasks().isEmpty()) {
                showDoneAnimation()
            }
        })
    }

    private fun showDoneAnimation() {
        done_animation_view.alpha = 1f
        done_animation_view.addAnimatorUpdateListener { animator ->
            if (animator.animatedFraction >= 1f) {
                done_animation_view.animate().alpha(0f).withEndAction {
                    done_animation_view.progress = 0f
                }.start()
            }
        }
        done_animation_view.playAnimation()
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
        task.taskState = TaskState.LATER.rawValue
        compositeDisposable.add(taskRepository.updateTask(task).subscribe())
    }

    override fun resumePressed(adapter: TaskAdapter, task: Task) {
        task.taskState = TaskState.ACTIVE.rawValue
        compositeDisposable.add(taskRepository.updateTask(task).subscribe())
    }

    override fun donePressed(adapter: TaskAdapter, task: Task) {
        task.taskState = TaskState.COMPLETE.rawValue
        val updateTask = taskRepository.updateTask(task)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ ->
                showDoneAnimationIfNeeded()
            }
        compositeDisposable.add(updateTask)
    }

    override fun cancelPressed(adapter: TaskAdapter, task: Task) {
        compositeDisposable.add(taskRepository.cancelTask(task).subscribe())
    }

    override fun resetPressed(adapter: TaskAdapter, task: Task) {
        task.taskState = TaskState.ACTIVE.rawValue
        compositeDisposable.add(taskRepository.updateTask(task).subscribe())
    }
}
