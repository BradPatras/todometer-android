package io.github.bradpatras.todometer

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var todoItems = 12
    private var doneItems = 0
    private var laterItems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        addtodo_btn.setOnClickListener {
            todoItems++
            updateTodoMeter()
        }

        done_btn.setOnClickListener {
            if (doneItems + laterItems >= todoItems) return@setOnClickListener
            doneItems++
            updateTodoMeter()
        }

        addlater_btn.setOnClickListener {
            if (laterItems + doneItems >= todoItems) return@setOnClickListener
            laterItems++
            updateTodoMeter()
        }

        subtractlater_btn.setOnClickListener {
            if (laterItems == 0) return@setOnClickListener
            laterItems--
            doneItems++
            updateTodoMeter()
        }
    }

    private fun updateTodoMeter() {
        todo_meter.doneMeterProgress = doneItems.toFloat() / todoItems.toFloat()
        todo_meter.laterMeterProgress = laterItems.toFloat() / todoItems.toFloat()
    }

    override fun onBackPressed() {
        todoItems = 12
        doneItems = 0
        laterItems = 0
        todo_meter.laterMeterProgress = laterItems.toFloat() / todoItems.toFloat()
        todo_meter.doneMeterProgress = doneItems.toFloat() / todoItems.toFloat()
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
