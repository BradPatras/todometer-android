package io.github.bradpatras.todometer

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.bradpatras.todometer.data.Task
import io.github.bradpatras.todometer.data.TaskRepository
import io.github.bradpatras.todometer.data.TaskState
import io.github.bradpatras.todometer.tasklist.TaskAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), TaskAdapter.ItemActionHandler {
    private var todoItems = 12
    private var doneItems = 0
    private var laterItems = 0

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
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter
        taskListAdapter = taskAdapter

        add_btn.setOnClickListener {
            if (add_et.text.isNotBlank()) {
                taskRepository.insertTask(Task(0, add_et.text.toString(), TaskState.ACTIVE.rawValue)).subscribe()
                add_et.text.clear()
                add_et.clearFocus()
                hideKeyboardFrom(add_et)
            }
        }

        taskRepository.allTasks.observe(this, Observer { tasks: List<Task> ->
                taskListAdapter?.tasks = tasks
        })
    }

    private fun hideKeyboardFrom(view: View) {
        (this.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
            it.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun updateTodoMeter() {
        todo_meter.doneMeterProgress = doneItems.toFloat() / todoItems.toFloat()
        todo_meter.laterMeterProgress = laterItems.toFloat() / todoItems.toFloat()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_reset -> true
            R.id.action_about -> true
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
        compositeDisposable.add(taskRepository.updateTask(task).subscribe())
    }

    override fun cancelPressed(adapter: TaskAdapter, task: Task) {
        compositeDisposable.add(taskRepository.cancelTask(task).subscribe())
    }
}
