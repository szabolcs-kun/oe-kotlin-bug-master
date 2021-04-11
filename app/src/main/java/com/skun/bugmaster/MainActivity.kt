package com.skun.bugmaster

import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))




        LoadInsectsTask().execute()

        /*
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        */
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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

    inner class LoadInsectsTask : AsyncTask<Int?, Void?, List<Insect>?>() {

        override fun doInBackground(vararg id: Int?): List<Insect>? {
            val dataSource = InsectDatabase.getInstance(application).insectDao

            return dataSource.get()
        }

        //@Override
        override fun onPostExecute(insects: List<Insect>?) {
            if (insects == null) return
            val adapter = InsectAdapter(insects)

            recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
            recyclerView!!.adapter = adapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView!!.setHasFixedSize(true)
        }
    }
}