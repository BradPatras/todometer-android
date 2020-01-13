package io.github.bradpatras.todometer

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.bradpatras.todometer.data.TaskRepository
import io.github.bradpatras.todometer.tasklist.TaskAdapter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private var todoItems = 12
    private var doneItems = 0
    private var laterItems = 0

    private var taskListAdapter: TaskAdapter? = null
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    @Inject
    lateinit var taskRepository: TaskRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        TodoMeterApplication.instance.applicationComponent.inject(this)

        compositeDisposable.addAll(Single.just(taskRepository)
            .observeOn(Schedulers.io())
            .map { it.doSomething() }
            .map { updateTaskList() }
            .subscribe())

        val taskAdapter = this.taskListAdapter ?: TaskAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter
        taskListAdapter = taskAdapter
    }

    private fun updateTaskList() {
        compositeDisposable.add(taskRepository.getActiveTasks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tasks ->
                taskListAdapter?.activeTasks = tasks
            })

        compositeDisposable.add(taskRepository.getLaterTasks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tasks ->
                taskListAdapter?.laterTasks = tasks
            })
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
}
